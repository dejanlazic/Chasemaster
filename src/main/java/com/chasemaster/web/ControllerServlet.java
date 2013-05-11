package com.chasemaster.web;

import static com.chasemaster.util.GameConst.*;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.chasemaster.exception.LoginException;
import com.chasemaster.exception.RegistrationException;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;
import com.chasemaster.persistence.model.Player;
import com.chasemaster.service.AuthenticationService;
import com.chasemaster.service.GameService;
import com.chasemaster.service.PlayerService;
import com.chasemaster.service.ServiceException;
import com.chasemaster.util.GameHelper;
import com.chasemaster.util.PageConst;

public class ControllerServlet extends HttpServlet implements PageConst {
  private static final long serialVersionUID = -6190407189326129661L;
  private static final Logger LOGGER = Logger.getLogger(ControllerServlet.class);

  private ServletContext context;
  private HttpSession session;

  private PlayerService playerService;
  private GameService gameService;
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

    /*
     * get initialization parameters from the deployment descriptor (web.xml) and put values in context
     */

    // the initial number of group players
    context.setAttribute(INIT_PARAM_PLAYERS_NUM, config.getInitParameter(INIT_PARAM_PLAYERS_NUM));
    LOGGER.debug(INIT_PARAM_PLAYERS_NUM + ": " + context.getAttribute(INIT_PARAM_PLAYERS_NUM));
    // duration for login
    context.setAttribute(INIT_PARAM_LOGIN_DURATION, config.getInitParameter(INIT_PARAM_LOGIN_DURATION));
    LOGGER.debug(INIT_PARAM_LOGIN_DURATION + ": " + context.getAttribute(INIT_PARAM_LOGIN_DURATION));

    // game administrator
    context.setAttribute(ADMIN_USERNAME, config.getInitParameter(ADMIN_USERNAME));
    context.setAttribute(ADMIN_PASSWORD, config.getInitParameter(ADMIN_PASSWORD));
    LOGGER.debug(ADMIN_USERNAME + ": " + context.getAttribute(ADMIN_USERNAME));
    LOGGER.debug(ADMIN_PASSWORD + ": " + context.getAttribute(ADMIN_PASSWORD));

    String jdbcDriverClassName = config.getInitParameter(JDBC_DRIVER);
    String databaseUrl = config.getInitParameter(DB_URL);
    String databaseUsername = config.getInitParameter(DB_USERNAME);
    String databasePassword = config.getInitParameter(DB_PASSWORD);

    String authDaoClassName = config.getInitParameter(AUTH_DAO);
    String playerDaoClassName = config.getInitParameter(PLAYER_DAO);
    String matchDaoClassName = config.getInitParameter(MATCH_DAO);
    String turnDaoClassName = config.getInitParameter(TURN_DAO);
    String movementDaoClassName = config.getInitParameter(MOVE_DAO);

    // set database configuration parameters
    DBConfig.setValues(jdbcDriverClassName, databaseUrl, databaseUsername, databasePassword, authDaoClassName, playerDaoClassName, matchDaoClassName,
        turnDaoClassName, movementDaoClassName);

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
      gameService = new GameService();
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

  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        processLoginPage(request);
      } else if ("login".equals(logicalName)) {
        processLogin(request, response);
      } else if ("registrationForm".equals(logicalName)) {
        processRegistrationPage();
      } else if ("register".equals(logicalName)) {
        processRegister(request);
      } else if ("getMatch".equals(logicalName)) {
        processGetMatch(request);
      } else if ("createMatch".equals(logicalName)) {
        processCreateMatch(request);
      } else {
        request.setAttribute("errorMessage", "Unknown action");
      }
    }

    LOGGER.info("Destination page: " + destinationPage);

    // AJAX does NOT use dispatching, so exclude Ajax calls
    // if (!logicalName.equals("async") && !logicalName.equals("asyncstart") && !logicalName.equals("asyncsend")) {
    if (!logicalName.equals("test")) {
      RequestDispatcher rd = getServletContext().getRequestDispatcher(destinationPage);
      rd.forward(request, response);
    }
  }

  // send login page
  private void processLoginPage(HttpServletRequest request) {
    LOGGER.info("Login page sending");

    // Display page if num of logged in users has not reach specified maximum number of players
    Map<String, Player> loggedPlayers = (Map<String, Player>) session.getAttribute("players");
    int maxPlayers = Integer.parseInt((String) context.getAttribute(INIT_PARAM_PLAYERS_NUM));
    LOGGER.debug("*************** " + loggedPlayers + ", " + maxPlayers);
    if (loggedPlayers != null && loggedPlayers.size() >= maxPlayers) {
      request.setAttribute("errorMessage", "Number of allowed logged is exceeded");
      destinationPage = ERROR_PAGE;
    } else {
      destinationPage = LOGIN_PAGE;
    }
  }

  @SuppressWarnings("unchecked")
  private void processLogin(HttpServletRequest request, HttpServletResponse response) {
    LOGGER.info("User authentication");
    destinationPage = ERROR_PAGE;
    boolean isAdmin = false;

    // collect request parameters
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    /*
     * 1) user is administrator
     */
    if (username.equals(context.getAttribute(ADMIN_USERNAME)) && password.equals(context.getAttribute(ADMIN_PASSWORD))) {
      LOGGER.info("Administrator authenticated.");

      try {
        request.setAttribute("matchesList", gameService.getMatches());
      } catch (ServiceException e) {
        LOGGER.error(e.getMessage());
      }

      isAdmin = true;
      destinationPage = ADMIN_PAGE;
    }

    /*
     * 2) user is player
     */

    if (!isAdmin) {
      // check if login time is within time period after creating a new match by admin
      Date loginStart = (Date) context.getAttribute(LOGIN_BEGIN_TIME);
      Date loginEnd = (Date) context.getAttribute(LOGIN_END_TIME);
      Date curTime = new Date();

      // Display page if num of logged in users has not reach specified maximum number of players
      Map<String, Player> loggedPlayers = (Map<String, Player>) session.getAttribute("players");
      int maxPlayers = Integer.parseInt((String) context.getAttribute(INIT_PARAM_PLAYERS_NUM));
      LOGGER.debug("*************** " + loggedPlayers + ", " + maxPlayers);

      if (loginStart == null || loginEnd == null) {
        request.setAttribute("errorMessage", "Login time not set yet");
      } else if (curTime.before(loginStart) || curTime.after(loginEnd)) {
        request.setAttribute("errorMessage", "Login time expired (" + loginStart + " - " + loginEnd + ")");
      } else if (loggedPlayers != null && loggedPlayers.size() >= maxPlayers) {
        request.setAttribute("errorMessage", "Number of allowed logged is exceeded");
      } else {
        try {
          Player player = authenticationService.login(username, password);
          if (player == null) {
            request.setAttribute("errorMessage", "Unknown error. Please contact system administrator.");
          } else {
            LOGGER.info("User authenticated. Player[" + player.getId() + ", " + player.getUsername() + "]");

            // cache userId on the client
            // Cookie cookie = new Cookie("playerId", Integer.toString(player.getId()));
            // response.addCookie(cookie);
            // request.setAttribute("playerId", Integer.toString(player.getId()));
            // LOGGER.debug("Sent cookie: " + cookie.getName() + "=" + cookie.getValue());

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
    }
  }

  // send registration page
  private void processRegistrationPage() {
    LOGGER.info("Send registration page");
    destinationPage = REGISTRATION_PAGE;
  }

  private void processRegister(HttpServletRequest request) {
    LOGGER.info("Create new user");

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

  private void processGetMatch(HttpServletRequest request) {
    // collect request parameters
    String matchId = request.getParameter("id");
    LOGGER.info("Retrieving a match for given parameter: id=" + matchId);

    // retrieve a match for given id
    try {
      if (matchId != null) {
        request.setAttribute("match", gameService.getMatch(Integer.parseInt(matchId)));
        request.setAttribute("matchesList", gameService.getMatches());
        destinationPage = ADMIN_PAGE;
      } else {
        request.setAttribute("errorMessage", "Match identifier is null");
        destinationPage = ERROR_PAGE; // TODO Handle this
      }
    } catch (ServiceException e) {
      request.setAttribute("errorMessage", e.getMessage());
      destinationPage = ERROR_PAGE;
    }
  }

  private void processCreateMatch(HttpServletRequest request) {
    LOGGER.info("Creating new match");

    try {
      // creates new match and retrieves all existing matches
      gameService.saveMatch(new Date());
      request.setAttribute("matchesList", gameService.getMatches());
      // sets current time as login begin time and current time plus configured duration for login end
      Date curDate = new Date();
      Calendar curCal = Calendar.getInstance();
      curCal.setTime(curDate);
      int loginDuration = Integer.parseInt((String) context.getAttribute(INIT_PARAM_LOGIN_DURATION));
      curCal.add(Calendar.MINUTE, loginDuration);
      context.setAttribute(LOGIN_BEGIN_TIME, curDate);
      context.setAttribute(LOGIN_END_TIME, curCal.getTime());
      LOGGER.debug(LOGIN_BEGIN_TIME + ": " + curDate);
      LOGGER.debug(LOGIN_END_TIME + ": " + curCal.getTime());

      destinationPage = ADMIN_PAGE;
    } catch (ServiceException e) {
      request.setAttribute("errorMessage", e.getMessage());
      destinationPage = ERROR_PAGE; // TODO Handle this
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