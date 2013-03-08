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

import org.apache.log4j.Logger;

import com.chasemaster.Movement;
import com.chasemaster.exception.NoObjectInContextException;
import com.chasemaster.util.GameHelper;
import com.mgs.chess.core.Location;

import static com.chasemaster.util.GameConst.*;

@SuppressWarnings("serial")
// If annotation is used, web.xml file should be excluded in build.xml#war
// @WebServlet(urlPatterns = { "/Game" }, asyncSupported = true)
public class GameServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(GameServlet.class);

  private List<AsyncContext> contexts = new LinkedList<AsyncContext>();
  ServletContext context;

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

      LOGGER.debug("Hidden playerid: " + request.getParameter("playerid"));
      
      // TEST
      LOGGER.debug("contexts.size: " + this.contexts.size());
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
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if ("move".equalsIgnoreCase(request.getParameter("operation"))) {
      LOGGER.debug("contexts.size: " + this.contexts.size());

      // put player id and belonging movement to a map
      // NOTE: use hidden field instead of cookie because older firefox does not handle cookies well
      // if more than one instance of FF is running
      
      String positionFrom = request.getParameter("positionFrom");
      String positionTo = request.getParameter("positionTo");
      String hiddenPlayerId = request.getParameter("playerid"); // from hidden field
      String playerId = ""; // from cookie
      Cookie[] cookies = request.getCookies();
      for (Cookie cookie : cookies) {
        if ("playerId".equals(cookie.getName())) {
          playerId = cookie.getValue();
          LOGGER.debug("Cookie = " + cookie.getName() + "=" + playerId);
          //break;
        }
      }
      LOGGER.debug("Cookie playerid=" + playerId + ": " + positionFrom + "," + positionTo);
      LOGGER.debug("Hidden playerid=" + hiddenPlayerId + ": " + positionFrom + "," + positionTo);
      
      
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

      //playerMovementPairs.put(playerId, positionTo);
      Location from = Location.forString(positionFrom);
      Location to = Location.forString(positionTo);
      LOGGER.debug("Locations converted: " + from + ", " + to);
      
      playerMovementPairs.put(hiddenPlayerId, new Movement(from, to, new Long(0))); // TODO Count duration

      // Check if all remaining (100 initially, but configurable), active users (requests) arrived before sending responses
      
      int numberOfMovements = playerMovementPairs.size();
      int numberOfActivePlayers = Integer.parseInt((String)context.getAttribute(INIT_PARAM_PLAYERS_NUM));
      
      LOGGER.debug("numberOfMovements: " + numberOfMovements);
      LOGGER.debug("numberOfActivePlayers: " + numberOfActivePlayers);

      
      if(helper.isTurnWhite() && numberOfMovements == 1) {
        LOGGER.debug("Current turn: WHITE");
      } else if(!helper.isTurnWhite() && numberOfMovements == numberOfActivePlayers) {
        LOGGER.debug("Current turn: BLACK");
      }
      
      if (numberOfMovements == numberOfActivePlayers) {
        // get a safe local copy of the list of AsyncContext
        List<AsyncContext> asyncContexts = new ArrayList<AsyncContext>(this.contexts);
        // clear the common list to prevent a pending request to be notified twice
        this.contexts.clear();

        // process all given movements
        //context = request.getSession().getServletContext();
        //playerMovementPairs = (Map<String, String>) context.getAttribute("playerMovementPairs");
        String htmlMessage = "";
        for(Map.Entry<String, Movement> entry : playerMovementPairs.entrySet()) {
          LOGGER.debug("From playerMovementPairs: " + entry.getKey() + ", " + entry.getValue());
          htmlMessage += "<b>User id:</b> " + entry.getKey() + ", <b>Position to:</b> " + entry.getValue() + "</br>";
        }
        
        // TODO: analyse movements
        //  1) maximum number of same field
        //    1b) if 2 or more fields with the same number of movements, take least total movements time
        // TODO: write movements in database
        // TODO: clear map
        
        
        try {
          LOGGER.debug(TURN_WHITE + ": " + helper.isTurnWhite());
          helper.changeTurn();
        } catch (NoObjectInContextException e) {
          // TODO FATAL system error - check code!
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