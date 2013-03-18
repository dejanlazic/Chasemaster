package com.chasemaster.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.chasemaster.exception.NoObjectInContextException;
import com.chasemaster.persistence.model.Player;
import com.chasemaster.util.GameHelper;
import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.Piece;
import com.mgs.chess.core.PieceNotFoundException;
import com.mgs.chess.core.movement.Movement;

import static com.chasemaster.util.GameConst.*;

@SuppressWarnings("serial")
// If annotation is used, web.xml file should be excluded in build.xml#war
// @WebServlet(urlPatterns = { "/Game" }, asyncSupported = true)
public class GameServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(GameServlet.class);

  private List<AsyncContext> contexts = new LinkedList<AsyncContext>();
  private ServletContext context;
  private HttpSession session;

  // all active players' ids and their movements
  private Map<String, Movement> playerMovementPairs = new HashMap<String, Movement>();
  private GameHelper helper;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    context = config.getServletContext();

    // initial number of group players
    context.setAttribute(INIT_PARAM_PLAYERS_NUM, config.getInitParameter(INIT_PARAM_PLAYERS_NUM));
    // current colour on move (starting with WHITE)
    context.setAttribute(TURN_WHITE, new Boolean(true));
    
    helper = new GameHelper(context);
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
      LOGGER.debug("Hidden playerid: " + request.getParameter("playerid"));
      
      // TEST
      LOGGER.debug("Contexts size: " + this.contexts.size());
      Cookie[] cookies = request.getCookies();
      for (Cookie cookie : cookies) {
        if ("playerId".equals(cookie.getName())) {
          LOGGER.debug("Cookie: " + cookie.getName() + "=" + cookie.getValue());
          break;
        }
      }      
    }
  }

  /*
   * Sending responses to all registered requests. 
   * NOTE: Check here if all remaining (100 initially, but configurable)
   * requests arrived before sending responses.
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if ("move".equalsIgnoreCase(request.getParameter("operation"))) {
      // get current or create new session object
      session = request.getSession(true);
      LOGGER.debug("====================");
      LOGGER.debug("Contexts size: " + this.contexts.size());

      /*
       * Input parameters
       */
      
      // put player id and belonging movement to a map
      // NOTE: use hidden field instead of cookie because older firefox does not handle cookies well
      // if more than one instance of FF is running
      
      String positionFrom = request.getParameter("positionFrom");
      String positionTo = request.getParameter("positionTo");
      String playerId = request.getParameter("playerid"); // from hidden field
      String playerIdCookie = ""; // from cookie
      Cookie[] cookies = request.getCookies();
      for (Cookie cookie : cookies) {
        if ("playerId".equals(cookie.getName())) {
          playerIdCookie = cookie.getValue();
          LOGGER.debug("Cookie: " + cookie.getName() + "=" + playerIdCookie);
          break;
        }
      }
      LOGGER.debug("Cookie playerId=" + playerIdCookie + ": " + positionFrom + "," + positionTo);
      LOGGER.debug("Hidden playerId=" + playerId + ": " + positionFrom + "," + positionTo);
      
      /*
       * Chess objects
       */
      Location locationFrom = Location.forString(positionFrom);
      Location locationTo = Location.forString(positionTo);
      Piece piece = helper.getPiece(locationFrom);
      LOGGER.debug("converted locations: " + locationFrom + " -> " + locationTo);
      LOGGER.debug("Piece moved: " + piece);

      // get a player from session
      Map<String, Player> players = (Map<String, Player>) session.getAttribute("players");
      if (players == null) {
        LOGGER.error("No players in session, creating new players map");
        // TODO Throw exception
      } 
      Player player = players.get(playerId);
      LOGGER.debug("Logged on player (in session): " + player);

//      Map<String, String> playerMovementPairs = null;
//      ServletContext context = request.getSession().getServletContext();
//      if (context.getAttribute("playerMovementPairs") == null) {
//        LOGGER.debug("---> In POST: creating new playerMovementPairs");
//        playerMovementPairs = new HashMap<String, String>();
//      } else {
//        LOGGER.debug("---> In POST: playerMovementPairs exists");
//        playerMovementPairs = (Map<String, String>) context.getAttribute("playerMovementPairs");
//      }
//      if(playerMovementPairs != null) {
//        playerMovementPairs.put(playerId, positionTo);    
//        context.setAttribute("playerMovementPairs", playerMovementPairs);
//      }

      if((helper.isTurnWhite() && player.isWhite()) 
          || (!helper.isTurnWhite() && !player.isWhite())) {
        LOGGER.debug("Current turn: " + (helper.isTurnWhite()? "WHITE" : "BLACK"));
        
        // TODO: check if movement is valid before putting it in a map
        ChessBoard board = helper.getBoard();
        playerMovementPairs.put(playerId, new Movement(piece, locationFrom, locationTo, new Long(0), playerId)); // TODO Count movement duration
      } 

      // Check if all remaining (100 initially, but configurable), 
      // active users (requests) arrived before sending responses
      
      int numberOfMovements = playerMovementPairs.size();
      // initial (configured) number of players, will be reduced as number of black players decreases
      int numberOfActivePlayers = Integer.parseInt((String)context.getAttribute(INIT_PARAM_PLAYERS_NUM));      
      LOGGER.debug("numberOfMovements: " + numberOfMovements);
      LOGGER.debug("numberOfActivePlayers: " + numberOfActivePlayers);
      
      // if all players moved
      if ((helper.isTurnWhite() && numberOfMovements == 1) 
          || (!helper.isTurnWhite() && numberOfMovements == numberOfActivePlayers)) {
        /*
         * Determine winning movement
         */
        List<Movement> winningMovements = helper.findWinningMovements(playerMovementPairs);
        LOGGER.debug("Determined winning movements: " + winningMovements);
        
        /*
         * make movement on the board
         */
//        ChessBoard newBoard = helper.getBoard().performMovement(winningMovement);
//        try {
//          LOGGER.debug("Piece on " + locationFrom + ": " + newBoard.getPieceOnLocation(locationFrom));
//        } catch(PieceNotFoundException e) {
//          LOGGER.debug("Piece on " + locationFrom + " not found on the board");
//        }
//        try {
//          LOGGER.debug("Piece on " + locationTo + ": " + newBoard.getPieceOnLocation(locationTo));
//        } catch(PieceNotFoundException e) {
//          LOGGER.debug("Piece on " + locationTo + " not found on the board");
//        }
        
        /*
         * send response to all players
         */
        
        // get a safe local copy of the list of AsyncContext
        List<AsyncContext> asyncContexts = new ArrayList<AsyncContext>(this.contexts);
        // clear the common list to prevent a pending request to be notified twice
        this.contexts.clear();

        // process all given movements
        //context = request.getSession().getServletContext();
        //playerMovementPairs = (Map<String, String>) context.getAttribute("playerMovementPairs");
        
        String htmlMessage = "<table border=\"1\">";
        htmlMessage += "<tr>";
        htmlMessage += "<th rowspan=\"2\">Username</th>";
        htmlMessage += "<th colspan=\"2\">Movement</th>";
        htmlMessage += "</tr>";
        htmlMessage += "<tr>";
        htmlMessage += "<th width=\"10px\">From</th>";
        htmlMessage += "<th width=\"10px\">To</th>";
        htmlMessage += "</tr>";        
        for(Map.Entry<String, Movement> entry : playerMovementPairs.entrySet()) {
          LOGGER.debug("From playerMovementPairs: " + entry.getKey() + ", " + entry.getValue());
          // TODO: Return JSON instead of HTML and username instead of id
          // TODO: Determine winning movements and hide the rest (grey rows)
          htmlMessage += "<tr><td>" + entry.getKey() + "</td><td>" + entry.getValue().getFrom() + "</td><td>" + entry.getValue().getTo() + "</td></tr>";
        }
        htmlMessage += "</table>";
        
        // TODO: analyze all movements
        //  1) maximum number of same field
        //    1b) if 2 or more fields with the same number of movements, take least total movements time
        // TODO: write movements in database
                
        try {
          LOGGER.debug(TURN_WHITE + ": " + helper.isTurnWhite());
          
          helper.changeTurn(); // switch colour for the next turn
          playerMovementPairs.clear(); // clear list of performed movements
        } catch (NoObjectInContextException e) {
          // TODO FATAL system error (check code) - exit the game
          LOGGER.error("Object " + e.getMessage() + " not found in context");
        }
        
        /*
         * For all the AsyncContexts queued write the message to their responses
         */
        for (AsyncContext asyncContext : asyncContexts) {
          try {
            LOGGER.debug("Before returning: " + htmlMessage);

            PrintWriter writer = asyncContext.getResponse().getWriter();
            writer.println(htmlMessage);
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