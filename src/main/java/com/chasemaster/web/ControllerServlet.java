package com.chasemaster.web;

import static com.chasemaster.util.GameConst.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

//import org.json.simple.JSONObject;

import com.chasemaster.exception.LoginException;
import com.chasemaster.exception.RegistrationException;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;
import com.chasemaster.persistence.model.Player;
import com.chasemaster.service.AuthenticationService;
import com.chasemaster.service.PlayerService;
import com.chasemaster.service.ServiceException;
import com.chasemaster.util.GameHelper;
import com.chasemaster.util.PageConst;

public class ControllerServlet extends HttpServlet implements PageConst {
  private static final long serialVersionUID = -6190407189326129661L;
  private static final Logger LOGGER = Logger.getLogger(ControllerServlet.class);
  private static final String INIT_PARAM_PLAYERS_NUM = "playersNum";

  private ServletContext context;
  private HttpSession session;

  private PlayerService playerService;
  private AuthenticationService authenticationService;
  private GameHelper helper;

  private String destinationPage = ERROR_PAGE; // default response page
  // private Subject subject;
  private String errMsg = null;

  /*
   * Initialize all necessary variables (executed only once)
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    context = config.getServletContext();

    // put in context the initial number of group players
    context.setAttribute(INIT_PARAM_PLAYERS_NUM, config.getInitParameter(INIT_PARAM_PLAYERS_NUM));
    LOGGER.debug(INIT_PARAM_PLAYERS_NUM + ": " + context.getAttribute(INIT_PARAM_PLAYERS_NUM));

    // get initialization parameters from the deployment descriptor (web.xml)
    String jdbcDriverClassName = config.getInitParameter("jdbcDriverClassName");
    String databaseUrl = config.getInitParameter("dbURL");
    String databaseUsername = config.getInitParameter("dbUsername");
    String databasePassword = config.getInitParameter("dbPassword");
    String authDaoClassName = config.getInitParameter("authDAOClassName");
    String playerDaoClassName = config.getInitParameter("playerDAOClassName");
    String matchDaoClassName = config.getInitParameter("matchDAOClassName");
    String turnDaoClassName = config.getInitParameter("turnDAOClassName");
    String movementDaoClassName = config.getInitParameter("movementDAOClassName");

    // set database configuration parameters
    DBConfig.setValues(jdbcDriverClassName, databaseUrl, databaseUsername, databasePassword, authDaoClassName,
        playerDaoClassName, matchDaoClassName, turnDaoClassName, movementDaoClassName);

    LOGGER.debug("jdbcDriverClassName: " + DBConfig.getJDBCDriverClassName());
    LOGGER.debug("databaseUrl: " + DBConfig.getDatabaseURL());
    LOGGER.debug("databaseUsername: " + DBConfig.getDatabaseUsername());
    LOGGER.debug("databasePassword: " + DBConfig.getDatabasePassword());
    LOGGER.debug("authDaoClassName: " + DBConfig.getAuthDaoClassName());
    LOGGER.debug("playerDaoClassName: " + DBConfig.getPlayerDaoClassName());
    LOGGER.debug("matchDaoClassName: " + DBConfig.getMatchDaoClassName());
    LOGGER.debug("turnDaoClassName: " + DBConfig.getTurnDaoClassName());
    LOGGER.debug("movementDaoClassName: " + DBConfig.getMovementDaoClassName());

    try {
      // DB conn and DAO configured in web.xml
      playerService = new PlayerService();
      authenticationService = new AuthenticationService();
    } catch (ServiceException e) {
      LOGGER.error(e.getMessage());
      errMsg = e.getMessage();
    }

    helper = new GameHelper(context);

    context.setAttribute(CHESSBOARD_IMAGES, helper.getBoardImages());
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    // get current or create new session object
    session = request.getSession(true);

    response.setContentType("text/html;charset=UTF-8");

    String logicalName = request.getServletPath();
    logicalName = logicalName.substring(logicalName.lastIndexOf('/') + 1, logicalName.indexOf('.'));
    LOGGER.debug("====================");
    LOGGER.debug("logicalName: " + logicalName);

    // if an error happened during initialization
    if (errMsg != null) {
      request.setAttribute("errorMessage", errMsg);
      // } else if(!logicalName.equals("login") && (session == null || session.getAttribute("subject") == null)) {
      // destinationPage = LOGIN_PAGE;
    } else {
      // check request parameter(s)
      if ("loginForm".equals(logicalName)) {
        processLoginPage();
      } else if ("login".equals(logicalName)) {
        processLogin(request, response);
      } else if ("registrationForm".equals(logicalName)) {
        processRegistrationPage();
      } else if ("register".equals(logicalName)) {
        processRegister(request);
      } else {
        request.setAttribute("errorMessage", "Unknown action");
      }
    }

    LOGGER.info("Destination page: " + destinationPage);

    // AJAX does NOT use dispatching, so exclude Ajax calls
    //if (!logicalName.equals("async") && !logicalName.equals("asyncstart") && !logicalName.equals("asyncsend")) {
    if (!logicalName.equals("test")) {
      RequestDispatcher rd = getServletContext().getRequestDispatcher(destinationPage);
      rd.forward(request, response);
    }
  }

  // send login page
  private void processLoginPage() {
    LOGGER.info("Send login page");
    // TODO: Check (session) if the user is not already logged in
    destinationPage = LOGIN_PAGE;
  }

  @SuppressWarnings("unchecked")
  private void processLogin(HttpServletRequest request, HttpServletResponse response) {
    LOGGER.info("Authenticate a user");
    destinationPage = ERROR_PAGE;

    // collect request parameters
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try {
      Player player = authenticationService.login(username, password);
      if (player == null) {
        request.setAttribute("errorMessage", "Unknown error. Please contact system administrator.");
      } else {
        LOGGER.info("User authenticated. Player[" + player.getId() + ", " + player.getUsername() + "]");

        // cache userId on the client
        Cookie cookie = new Cookie("playerId", Integer.toString(player.getId()));
        response.addCookie(cookie);
        request.setAttribute("playerId", Integer.toString(player.getId()));
        LOGGER.debug("Sent cookie: " + cookie.getName() + "=" + cookie.getValue());

        // cache all logged on players in session
        Map<String, Player> players = (Map<String, Player>) session.getAttribute("players");
        if (players == null) {
          LOGGER.debug("No players in session, creating new players map");
          players = new HashMap<String, Player>();
        }
        players.put(Integer.toString(player.getId()), player);
        session.setAttribute("players", players);
        LOGGER.debug("Players map put in session, size: " + players.size());

        destinationPage = GAME_PAGE;
      }
    } catch (ServiceException e) {
      // TODO Do something to fix this
      request.setAttribute("errorMessage", e.getMessage());
    } catch (LoginException e) {
      request.setAttribute("errorMessage", e.getMessage());
    }
  }

  // send registration page
  private void processRegistrationPage() {
    LOGGER.info("Send registration page");
    destinationPage = REGISTRATION_PAGE;
  }

  private void processRegister(HttpServletRequest request) {
    LOGGER.info("Create a new user");

    // collect request parameters
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // retrieve role id from db for use in relationship with user
    try {
      String s = playerService.register(username, password);
      destinationPage = LOGIN_PAGE;
      LOGGER.debug("Registration done. " + s);
    } catch (ServiceException e) {
      request.setAttribute("errorMessage", e.getMessage());
      destinationPage = ERROR_PAGE; // TODO Handle this
    } catch (RegistrationException e) {
      request.setAttribute("errorMessage", e.getMessage());
      destinationPage = ERROR_PAGE;
    }
  }

  /**
   * Closes all database connections. This method is invoked when the Servlet is unloaded.
   */
  public void destroy() {
    super.destroy();
    DBUtil.closeAllConnections();
  }
}