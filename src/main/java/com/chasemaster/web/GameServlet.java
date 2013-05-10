package com.chasemaster.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.chasemaster.exception.NoMovementException;
import com.chasemaster.exception.NoObjectInContextException;
import com.chasemaster.exception.RegistrationException;
import com.chasemaster.persistence.model.Player;
import com.chasemaster.service.GameService;
import com.chasemaster.service.PlayerService;
import com.chasemaster.service.ServiceException;
import com.chasemaster.util.GameHelper;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.Piece;
import com.mgs.chess.core.PieceNotFoundException;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.movement.Movement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import static com.chasemaster.util.GameConst.*;

@SuppressWarnings("serial")
// If annotation is used, web.xml file should be excluded in build.xml#war
// @WebServlet(urlPatterns = { "/Game" }, asyncSupported = true)
public class GameServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(GameServlet.class);

  private GameService gameService;
  private PlayerService playerService;

  private List<AsyncContext> contexts = new LinkedList<AsyncContext>();
  private ServletContext context;
  private HttpSession session;

  // all active players' ids and their movements
  private Map<String, Movement> playerMovementPairs = new HashMap<String, Movement>();
  private Map<String, Movement> failedPlayerMovementPairs = new HashMap<String, Movement>();
  private GameHelper helper;
  private int numberOfActivePlayers = 0;
  private boolean gameOver = false;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    context = config.getServletContext();
    helper = new GameHelper(context);
    
    // current colour on move (starting with WHITE)
    context.setAttribute(TURN_WHITE, new Boolean(true));
    // this time is used to measure durations of all BLACK movements to find a group
    // with the shortest movements; since the first move is WHITE, time is not important and can be set in init,
    // but all other times during a match it is reset before sending response to all players
    context.setAttribute(START_TIME, new Date());

    // initial number of group players (configured in web.xml and ControllerServlet)
    // will be decreasing in doPost() as a game progress
    numberOfActivePlayers = Integer.parseInt((String) context.getAttribute(INIT_PARAM_PLAYERS_NUM));

    try {
      // Note: DB conn and DAO configured in web.xml and ControllerServlet
      gameService = new GameService();
      playerService = new PlayerService();
    } catch (ServiceException e) {
      LOGGER.error(e.getMessage());
    }
    
    try {
      Integer maxMatchId = gameService.getMaxMatchId(); // retrieve current match id
      context.setAttribute(MATCH_ID, maxMatchId);
    } catch (ServiceException e) {
      LOGGER.error(e.getMessage());
    }
  }

  /*
   * Requests registering.
   * 
   * When the Servlet receives a "get" Request, it starts an AsyncContext by calling: request.startAsync(request,
   * response). This will notify the Web Container that at the end of the request call it should free the handling
   * thread and leave the connection open so that other thread writes the response and end the connection.
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if ("start".equalsIgnoreCase(request.getParameter("operation"))) {
      // create asynchronous context and add it to a list so we can get all waiting contexts in the doPost method
      final AsyncContext asyncContext = request.startAsync(request, response);
      asyncContext.setTimeout(10 * 60 * 1000); // 10 minutes
      contexts.add(asyncContext);

      LOGGER.debug("====================");
      LOGGER.debug("playerid registered to asyncContext: " + request.getParameter("playerid"));
    }
  }

  /*
   * Sending responses to all registered requests. NOTE: Check here if all remaining (100 initially, but configurable)
   * requests arrived before sending responses.
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if ("move".equalsIgnoreCase(request.getParameter("operation"))) {
      // get current or create new session object
      session = request.getSession(true);
      LOGGER.debug("====================");
      LOGGER.debug("Async contexts size: " + contexts.size());

      /*
       * Input parameters
       */

      // put player id and belonging movement to a map
      // NOTE: use hidden field instead of cookie because older firefox does not handle cookies well
      // if more than one instance of FF is running
      String positionFrom = request.getParameter("positionFrom");
      String positionTo = request.getParameter("positionTo");
      String t = request.getParameter("t");
      LOGGER.debug("Parameter - time: " + t);
      String tms = request.getParameter("tms");
      long endTimeMS = Long.parseLong(tms);
      LOGGER.debug("End time: " + endTimeMS);
      Date startTime = (Date) context.getAttribute(START_TIME);
      long startTimeMS = startTime.getTime();
      LOGGER.debug("Start time: " + startTimeMS);
      LOGGER.debug("Duration: " + (endTimeMS - startTimeMS));
      String playerId = request.getParameter("playerid"); // from hidden field
      LOGGER.debug("playerId=" + playerId + ": " + positionFrom + "," + positionTo);

      // Chess objects
      Location locationFrom = Location.forString(positionFrom);
      Location locationTo = Location.forString(positionTo);
      Piece piece = helper.getPiece(locationFrom);
      LOGGER.debug("Converted locations: " + locationFrom + " -> " + locationTo);
      LOGGER.debug("Piece moved: " + piece);

      // get a player from session
      Map<String, Player> players = (Map<String, Player>) session.getAttribute("players");
      if (players == null) {
        LOGGER.error("No players in session, creating new players map");
        // TODO Throw exception
      }
      Player player = players.get(playerId);
      LOGGER.debug("Logged on player (in session): " + player);

      // Map<String, String> playerMovementPairs = null;
      // ServletContext context = request.getSession().getServletContext();
      // if (context.getAttribute("playerMovementPairs") == null) {
      // LOGGER.debug("---> In POST: creating new playerMovementPairs");
      // playerMovementPairs = new HashMap<String, String>();
      // } else {
      // LOGGER.debug("---> In POST: playerMovementPairs exists");
      // playerMovementPairs = (Map<String, String>) context.getAttribute("playerMovementPairs");
      // }
      // if(playerMovementPairs != null) {
      // playerMovementPairs.put(playerId, positionTo);
      // context.setAttribute("playerMovementPairs", playerMovementPairs);
      // }

      /*
       * check if movement is valid before putting it in a map
       */
      LOGGER.debug("Before filling movements lists: playerMovementPairs.size: " + playerMovementPairs.size() + ", failedPlayerMovementPairs.size: "
          + failedPlayerMovementPairs.size());
      if ((helper.isTurnWhite() && player.isWhite()) || (!helper.isTurnWhite() && !player.isWhite())) {
        if (helper.isMovementValid(locationFrom, locationTo)) {
          playerMovementPairs.put(playerId, new Movement(piece, locationFrom, locationTo, endTimeMS - startTimeMS, playerId));
        } else {
          failedPlayerMovementPairs.put(playerId, new Movement(piece, locationFrom, locationTo, endTimeMS - startTimeMS, playerId));
        }
      }

      // Check if all remaining active users (requests) arrived before sending responses
      int numberOfMovements = playerMovementPairs.size() + failedPlayerMovementPairs.size();
      LOGGER.debug("numberOfMovements - total: " + numberOfMovements);
      LOGGER.debug("numberOfMovements - correct: " + playerMovementPairs.size());
      LOGGER.debug("numberOfMovements - false: " + failedPlayerMovementPairs.size());
      LOGGER.debug("numberOfActivePlayers: " + numberOfActivePlayers);
      LOGGER.debug("Current turn: " + (helper.isTurnWhite() ? "WHITE" : "BLACK"));

      // if all players moved
      if ((helper.isTurnWhite() && numberOfMovements == 1) || (!helper.isTurnWhite() && numberOfMovements == numberOfActivePlayers)) {
        /*
         * Determine both winning and losing movement
         */
        List<Movement> winningMovements = new ArrayList<Movement>();
        List<Movement> losingMovements = new ArrayList<Movement>();
        try {
          helper.findWinningMovements(winningMovements, losingMovements, playerMovementPairs, failedPlayerMovementPairs);
        } catch (NoMovementException e1) {
          //gameOver = true;
        }

        LOGGER.debug("Determined winning movements: " + winningMovements);
        LOGGER.debug("Determined losing movements: " + losingMovements);

        // decrease number of black players
        if (!helper.isTurnWhite()) {
          numberOfActivePlayers = winningMovements.size();
          LOGGER.debug("numberOfActivePlayers changed: " + numberOfActivePlayers);
        }
        
        Movement winner = null;
        JSONObject jsonResponse = new JSONObject();

        if (winningMovements != null && winningMovements.size() > 0) {
          // check if targeted field is empty
          try {
            PieceOnLocation pieceOnTargetedField = helper.getBoard().getPieceOnLocation(locationTo);
            LOGGER.debug("Is targeted position empty? " + (pieceOnTargetedField == null));
            if (pieceOnTargetedField != null) {
              // remove a piece from the board
              helper.removePiece(locationTo);
              LOGGER.debug(pieceOnTargetedField + " removed");

              // test if removed
              try {
                pieceOnTargetedField = helper.getBoard().getPieceOnLocation(locationTo);
                LOGGER.debug("ERROR: Targeted position should be empty.");
              } catch (com.mgs.chess.core.PieceNotFoundException e) {
                pieceOnTargetedField = null; // if it took some other piece
                LOGGER.debug("OK: Targeted position is now empty.");
              }
            }
          } catch (PieceNotFoundException e) {
            LOGGER.debug("Targeted field \'" + locationTo + "\' is empty");
          }

          /*
           * make movement on the board
           */
          Movement winMovement = winningMovements.get(0);
          LOGGER.debug("Perform movement on board: " + winMovement);
          helper.performMovement(winMovement);

          try {
            LOGGER.debug("Piece on FROM(" + locationFrom + "): " + helper.getBoard().getPieceOnLocation(locationFrom));
          } catch (PieceNotFoundException e) {
            LOGGER.debug("Piece on FROM(" + locationFrom + ") not found on the board");
          }
          try {
            LOGGER.debug("Piece on TO(" + locationTo + "): " + helper.getBoard().getPieceOnLocation(locationTo));
          } catch (PieceNotFoundException e) {
            LOGGER.debug("Piece on TO(" + locationTo + ") not found on the board");
          }

          /*
           * check if end of game
           */
          if (helper.isInChess()) {
            LOGGER.debug((helper.isTurnWhite() ? "BLACK" : "WHITE") + " is in chess");
          }
          if (helper.isInChess() && helper.isCheckMate()) {
            LOGGER.debug((helper.isTurnWhite() ? "BLACK" : "WHITE") + " is in check mate");
          }

          /*
           * make JSON result and list of players for session
           */
          jsonResponse.put("movementFrom", winningMovements.get(0).getFrom().toString());
          jsonResponse.put("movementTo", winningMovements.get(0).getTo().toString());
          JSONArray jsonWinningList = new JSONArray();
          for (Movement winningMovement : winningMovements) {
            jsonWinningList.add(winningMovement.getPlayerId());
          }
          jsonResponse.put("winningPlayers", jsonWinningList);
          context.setAttribute(WINNERS, winningMovements);
        } else {
          /*
           * end of game
           */
          gameOver = true;
          
          // take previous' turn winners
          List<Movement> winners = (List<Movement>)context.getAttribute(WINNERS);
          // if more than one black player, pick only one with shortest movement duration
          long shortestDuration = Long.MAX_VALUE;
          for(Movement move : winners) {
            if(move.getDuration() < shortestDuration) {
              winner = move;
            }
          } 
          LOGGER.debug("Game over, winner: " + winner);
          
          JSONArray jsonWinningList = new JSONArray();
          jsonWinningList.add(winner.getPlayerId());
          jsonResponse.put("movementFrom", "");
          jsonResponse.put("movementTo", "");
          jsonResponse.put("winningPlayers", jsonWinningList);
        }
        if (losingMovements.size() > 0) {
          JSONArray jsonLosingList = new JSONArray();
          for (Movement losingMovement : losingMovements) {
            jsonLosingList.add(losingMovement.getPlayerId());
          }
          jsonResponse.put("losingPlayers", jsonLosingList);
        } else {
          jsonResponse.put("losingPlayers", new JSONArray());
        }
        jsonResponse.put("gameOver", gameOver);
        LOGGER.debug("JSON: " + jsonResponse.toJSONString());

        /*
         * write to database
         */
        // movements
        List<String> winningList = new ArrayList<String>(); // only contains IDs
        for (Movement winningMovement : winningMovements) {
          winningList.add(winningMovement.getPlayerId());
        }
        try {
          int turnId = gameService.saveTurn(((Integer) context.getAttribute(MATCH_ID)), 
              (helper.isTurnWhite() ? "WHITE" : "BLACK"), winningList);

          for (Movement winningMovement : winningMovements) {
            gameService.saveMovement(turnId, Integer.parseInt(winningMovement.getPlayerId()), 
                winningMovement.getFrom().toString(), winningMovement.getTo().toString(), winningMovement.getDuration());
          }
          for (Movement losingMovement : losingMovements) {
            gameService.saveMovement(turnId, Integer.parseInt(losingMovement.getPlayerId()), 
                losingMovement.getFrom().toString(), losingMovement.getTo().toString(), losingMovement.getDuration());
          }
        } catch (ServiceException se) {
          LOGGER.error(se.getMessage());
        }
        
        // winner as WHITE player for the next match (if end of game)
        if(gameOver) {
          LOGGER.debug("End of game. Write winner as WHITE player to database: " );
          try {
            Player winPlayer = playerService.find(Integer.parseInt(winner.getPlayerId()));
            LOGGER.debug("************ Player to save: " + winPlayer + " password: " + winPlayer.getPassword());
            playerService.save(winPlayer.getUsername(), winPlayer.getPassword(), winPlayer.getColour());
          } catch (NumberFormatException e) {
            LOGGER.error("Parsing id: " + e.getMessage());
          } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
          } catch (RegistrationException e) {
            LOGGER.error("Saving: " + e.getMessage());
          }
        }

        /*
         * send response to all players
         */

        // get a safe local copy of the list of AsyncContext
        List<AsyncContext> asyncContexts = new ArrayList<AsyncContext>(contexts);
        // clear the common list to prevent a pending request to be notified twice
        contexts.clear();

        // process all given movements
        // context = request.getSession().getServletContext();
        // playerMovementPairs = (Map<String, String>) context.getAttribute("playerMovementPairs");

        // TODO: analyze all movements
        // 1) maximum number of same field
        // 1b) if 2 or more fields with the same number of movements, take least total movements time
        // TODO: write movements in database

        try {
          helper.changeTurn(); // switch colour for the next turn
          playerMovementPairs.clear(); // clear lists of performed movements
          failedPlayerMovementPairs.clear();
          context.setAttribute(START_TIME, new Date()); // reset start time
        } catch (NoObjectInContextException e) {
          // TODO FATAL system error (check code) - exit the game
          LOGGER.error("Object " + e.getMessage() + " not found in context");
        }

        /*
         * For all the AsyncContexts queued write the message to their responses
         */
        for (AsyncContext asyncContext : asyncContexts) {
          try {
            PrintWriter writer = asyncContext.getResponse().getWriter();
            writer.println(jsonResponse.toJSONString());
            writer.flush();

            // complete queued requests
            asyncContext.complete();
          } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
          }
        }
      }
    }
  }
}