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
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
// If annotation is used, web.xml file should be excluded in build.xml#war
// @WebServlet(urlPatterns = { "/Game" }, asyncSupported = true)
public class GameServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(GameServlet.class);
  private static final String INIT_PARAM_PLAYERS_NUM = "playersNum";

  // private List<AsyncContext> contexts = new LinkedList<>(); // JDK 7
  private List<AsyncContext> contexts = new LinkedList<AsyncContext>();
  ServletContext context;

  // all active players' ids and their movements
  private Map<String, String> playerMovementPairs = new HashMap<String, String>();

  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    // put in context the initial number of group players
    context = config.getServletContext();
    context.setAttribute(INIT_PARAM_PLAYERS_NUM, config.getInitParameter(INIT_PARAM_PLAYERS_NUM));
  }

  /*
   * Registering requests.
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

      // TEST
      LOGGER.debug("---> In GET: contexts.size: " + this.contexts.size());
      Cookie[] cookies = request.getCookies();
      for (Cookie cookie : cookies) {
        if ("playerId".equals(cookie.getName())) {
          LOGGER.debug("---> In GET: cookie = " + cookie.getValue());
          //break;
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
      LOGGER.debug("---> In POST: contexts.size = " + this.contexts.size());

      // put player id and belonging movement to a map
      
      String positionFrom = request.getParameter("positionFrom");
      String positionTo = request.getParameter("positionTo");
      String playerId = "";
      Cookie[] cookies = request.getCookies();
      for (Cookie cookie : cookies) {
        if ("playerId".equals(cookie.getName())) {
          playerId = cookie.getValue();
          LOGGER.debug("---> In POST: cookie = " + playerId);
          //break;
        }
      }
      LOGGER.debug(playerId + ": " + positionFrom + "," + positionTo);
      
      
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

      playerMovementPairs.put(playerId, positionTo);    

      // Check if all remaining (100 initially, but configurable), active users (requests) arrived before sending responses
      
      int numberOfMovements = playerMovementPairs.size();
      int numberOfActivePlayers = Integer.parseInt((String)context.getAttribute(INIT_PARAM_PLAYERS_NUM));
      
      LOGGER.debug("---> In POST: numberOfMovements = " + numberOfMovements);
      LOGGER.debug("---> In POST: numberOfActivePlayers = " + numberOfActivePlayers);

      
      if (numberOfMovements == numberOfActivePlayers) {
        // List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts); // JDK 7
        // get a safe copy of the list of AsyncContext
        List<AsyncContext> asyncContexts = new ArrayList<AsyncContext>(this.contexts);
        // clear the common list to prevent a pending request to be notified twice
        this.contexts.clear();

        // process all given movements
        //context = request.getSession().getServletContext();
        //playerMovementPairs = (Map<String, String>) context.getAttribute("playerMovementPairs");
        String htmlMessage = "";
        for(Map.Entry<String, String> entry : playerMovementPairs.entrySet()) {
          htmlMessage += "<b>User id:</b> " + entry.getKey() + ", <b>Position to:</b> " + entry.getValue() + "</br>";
        }

        /*
         * For all the AsyncContexts queued write the message to their responses
         */
        for (AsyncContext asyncContext : asyncContexts) {
          try {
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