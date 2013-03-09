package com.chasemaster.web;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

//import org.json.simple.JSONObject;

import com.chasemaster.Movement;
import com.chasemaster.exception.LoginException;
import com.chasemaster.exception.RegistrationException;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;
import com.chasemaster.persistence.model.Player;
import com.chasemaster.service.AuthenticationService;
import com.chasemaster.service.PlayerService;
import com.chasemaster.service.ServiceException;
import com.chasemaster.util.PageConst;

public class ControllerServlet extends HttpServlet implements PageConst {
  private static final long serialVersionUID = -6190407189326129661L;
  private static final Logger LOGGER = Logger.getLogger(ControllerServlet.class);
  private static final String INIT_PARAM_PLAYERS_NUM = "playersNum";

  private ServletContext context;
  private HttpSession session;

  private PlayerService playerService;
  private AuthenticationService authenticationService;

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
    LOGGER.debug(INIT_PARAM_PLAYERS_NUM + "=" + context.getAttribute(INIT_PARAM_PLAYERS_NUM));

    // get initialization parameters from the deployment descriptor (web.xml)
    String jdbcDriverClassName = config.getInitParameter("jdbcDriverClassName");
    String databaseUrl = config.getInitParameter("dbURL");
    String databaseUsername = config.getInitParameter("dbUsername");
    String databasePassword = config.getInitParameter("dbPassword");
    String authDaoClassName = config.getInitParameter("authDAOClassName");
    String playerDaoClassName = config.getInitParameter("playerDAOClassName");
    String gameDaoClassName = config.getInitParameter("gameDAOClassName");

    // set database configuration parameters
    DBConfig.setValues(jdbcDriverClassName, databaseUrl, databaseUsername, databasePassword, authDaoClassName,
        playerDaoClassName, gameDaoClassName);

    LOGGER.debug("jdbcDriverClassName=" + DBConfig.getJDBCDriverClassName());
    LOGGER.debug("databaseUrl=" + DBConfig.getDatabaseURL());
    LOGGER.debug("databaseUsername=" + DBConfig.getDatabaseUsername());
    LOGGER.debug("databasePassword=" + DBConfig.getDatabasePassword());
    LOGGER.debug("authDaoClassName=" + DBConfig.getAuthDaoClassName());
    LOGGER.debug("playerDaoClassName=" + DBConfig.getPlayerDaoClassName());
    LOGGER.debug("gameDaoClassName=" + DBConfig.getGameDaoClassName());

    try {
      playerService = new PlayerService();
      authenticationService = new AuthenticationService();
    } catch (ServiceException e) {
      LOGGER.error(e.getMessage());
      errMsg = e.getMessage();
    }
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
        // } else if (action.equals("login")) {
        // processLogin(request);
        // } else if (action.equals("logout")) {
        // processLogout();
        // } else if (action.equals("home_page")) {
        // processHomePage();
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
    if (!logicalName.equals("async") && !logicalName.equals("asyncstart") && !logicalName.equals("asyncsend")) {
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

        // create and cache a map with initial set of pieces (images) on a board
        Map<String, String> pieces = new HashMap<String, String>();
        pieces.put("A8", "brook.gif");
        pieces.put("B8", "bknight.gif");
        pieces.put("C8", "bbishop.gif");
        pieces.put("D8", "bqueen.gif");
        pieces.put("E8", "bking.gif");
        pieces.put("F8", "bbishop.gif");
        pieces.put("G8", "bknight.gif");
        pieces.put("H8", "brook.gif");
        pieces.put("A7", "bpawn.gif");
        pieces.put("B7", "bpawn.gif");
        pieces.put("C7", "bpawn.gif");
        pieces.put("D7", "bpawn.gif");
        pieces.put("E7", "bpawn.gif");
        pieces.put("F7", "bpawn.gif");
        pieces.put("G7", "bpawn.gif");
        pieces.put("H7", "bpawn.gif");
        pieces.put("A2", "wpawn.gif");
        pieces.put("B2", "wpawn.gif");
        pieces.put("C2", "wpawn.gif");
        pieces.put("D2", "wpawn.gif");
        pieces.put("E2", "wpawn.gif");
        pieces.put("F2", "wpawn.gif");
        pieces.put("G2", "wpawn.gif");
        pieces.put("H2", "wpawn.gif");
        pieces.put("A1", "wrook.gif");
        pieces.put("B1", "wknight.gif");
        pieces.put("C1", "wbishop.gif");
        pieces.put("D1", "wqueen.gif");
        pieces.put("E1", "wking.gif");
        pieces.put("F1", "wbishop.gif");
        pieces.put("G1", "wknight.gif");
        pieces.put("H1", "wrook.gif");
        session.setAttribute("pieces", pieces);

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

  // private void processNotesPage(HttpServletRequest request) {
  // LOGGER.info("Send home visitor home page");
  //
  // Subject subject = (Subject) session.getAttribute("subject");
  // System.out.println("---> Role: " + subject.getRole());
  // if (subject == null) {
  // destination = LOGIN_PAGE;
  // } else if (subject.getRole() == Role.HOME_VISITOR) {
  // // retrieve all children of the current user
  // try {
  // // retrieve and put list into session for use in jsp
  // List<Child> children = dataFacade.findChildren(subject.getUserId());
  // session.setAttribute("children", children);
  //
  // destination = NOTES_HOME_VISITOR_PAGE;
  // } catch (UserDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // } else if (subject.getRole() == Role.SUPERVISOR) {
  // try {
  // List<User> users = dataFacade.findAllUsers();
  // session.setAttribute("users", users);
  //
  // List<Child> children = dataFacade.findAllChildren();
  // session.setAttribute("children", children);
  // } catch (UserDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  //
  // destination = NOTES_SUPERVISOR_PAGE;
  // }
  // }
  //
  // private void processNotesViewerPage(HttpServletRequest request) {
  // LOGGER.info("Send notes viewer page");
  //
  // Subject subject = (Subject) session.getAttribute("subject");
  // if (subject == null) {
  // destination = LOGIN_PAGE;
  // } else {
  // // retrieve all children of the current user
  // try {
  // // retrieve and put list into session for use in jsp (finish
  // // caching!)
  // List<Note> notes = dataFacade.findNotesForUser(subject.getUserId());
  // session.setAttribute("notes", notes);
  //
  // destination = NOTES_VIEWER_PAGE;
  // } catch (NoteDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // }
  // }
  //
  // private void processChangePassword(HttpServletRequest request) {
  // LOGGER.info("Change password");
  //
  // // collect request parameters
  // String newPassword = request.getParameter("new_password");
  // String confirmPassword = request.getParameter("confirm_password");
  // LOGGER.info(newPassword);
  // LOGGER.info(confirmPassword);
  //
  // Subject subject = (Subject) session.getAttribute("subject");
  // LOGGER.info("Subject from session: " + subject);
  //
  // LOGGER.info("Change password for user id: " + subject.getUserId());
  // if (newPassword.equals(confirmPassword)) {
  // try {
  // dataFacade.changePassword(subject.getUserId(), newPassword);
  //
  // // TODO: Remove; this should happen on DAO layer
  // subject.setPasswordChanged(true);
  // session.setAttribute("subject", subject);
  //
  // // TODO: Put this in a separate method
  // if (!subject.isPasswordChanged()) {
  // destination = CHANGE_PWD_PAGE;
  // } else {
  // destination = HOME_PAGE;
  // }
  // } catch (LoginException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // } else {
  // request.setAttribute("errorMessage", "Password does not match.");
  // destination = ERROR_PAGE;
  // }
  // }
  //
  // // send new user page
  // private void processNewUserPage(HttpServletRequest request) {
  // LOGGER.info("Send new user page");
  //
  // // populate the form with roles retrieved from db
  // try {
  // List<Role> roles = dataFacade.findAllRoles();
  // // put list into request for use in jsp
  // request.setAttribute("roles", roles);
  // request.setAttribute("action", "new_user");
  // } catch (UserDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  //
  // destination = NEW_USER_PAGE;
  // }

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

  // private void processNewChildPage(HttpServletRequest request, HttpServletResponse response) {
  // LOGGER.info("Send new child page");
  //
  // // populate the form with roles retrieved from db
  // try {
  // List<User> users = dataFacade.findAllUsers();
  // request.setAttribute("users", users);
  // request.setAttribute("action", "new_child");
  //
  // destination = NEW_CHILD_PAGE;
  // } catch (UserDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  //
  // // try {
  // // // getServletConfig().getServletContext().getRequestDispatcher(
  // // // "/Controller?action=edit_child_page").forward(request, response);
  // // getServletConfig().getServletContext().getRequestDispatcher(
  // // "/Controller").forward(request, response);
  // // } catch (ServletException e) {
  // // // TODO Auto-generated catch block
  // // e.printStackTrace();
  // // } catch (IOException e) {
  // // // TODO Auto-generated catch block
  // // e.printStackTrace();
  // // }
  // }

  // private void processNewChild(HttpServletRequest request) {
  // LOGGER.info("Create a new child");
  //
  // // collect request parameters
  // String fName = request.getParameter("first_name");
  // String lName = request.getParameter("last_name");
  // String address = request.getParameter("address");
  // String userIdStr = request.getParameter("user_id");
  // int userId = 0;
  // try {
  // userId = Integer.parseInt(userIdStr);
  // } catch (NumberFormatException e) {
  // LOGGER.error(e.getMessage());
  // request.setAttribute("Wrong user ID retrieved", errMsg);
  // destination = ERROR_PAGE;
  // }
  // // NOTE: date format must be the same as specified in JQuery Datepicker
  // String dateOfBirthStr = request.getParameter("date_of_birth");
  // Date dateOfBirth = null;
  // if (dateOfBirthStr != null) {
  // try {
  // dateOfBirth = FRPUtil.convertStringToDate(dateOfBirthStr);
  // } catch (ParseException e) {
  // LOGGER.error(e.getMessage());
  // request.setAttribute("Wrong date format", errMsg);
  // destination = ERROR_PAGE;
  // }
  // }
  //
  // LOGGER.debug("fName: " + fName);
  // LOGGER.debug("lName: " + lName);
  // LOGGER.debug("address: " + address);
  // LOGGER.debug("dateOfBirth: " + dateOfBirth);
  // LOGGER.debug("userId: " + userId);
  //
  // Child child = new Child();
  // child.setFirstName(fName);
  // child.setLastName(lName);
  // child.setAddress(address);
  // child.setDateOfBirth(dateOfBirth);
  // child.setUser(new User(userId));
  //
  // // retrieve role id from db for use in relationship with user
  // try {
  // dataFacade.createChild(child);
  //
  // destination = ADMIN_PAGE;
  // } catch (UserDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // }
  //
  // private void processUpdateChild(HttpServletRequest request) {
  // LOGGER.info("Update an existing child");
  //
  // // collect request parameters
  // String fName = request.getParameter("first_name");
  // String lName = request.getParameter("last_name");
  // String address = request.getParameter("address");
  // String userIdStr = request.getParameter("user_id");
  // int userId = 0;
  // try {
  // userId = Integer.parseInt(userIdStr);
  // } catch (NumberFormatException e) {
  // LOGGER.error(e.getMessage());
  // request.setAttribute("Wrong user ID retrieved", errMsg);
  // destination = ERROR_PAGE;
  // }
  // // NOTE: date format must be the same as specified in JQuery Datepicker
  // String dateOfBirthStr = request.getParameter("date_of_birth");
  // Date dateOfBirth = null;
  // if (dateOfBirthStr != null) {
  // try {
  // dateOfBirth = FRPUtil.convertStringToDate(dateOfBirthStr);
  // } catch (ParseException e) {
  // LOGGER.error(e.getMessage());
  // request.setAttribute("Wrong date format", errMsg);
  // destination = ERROR_PAGE;
  // }
  // }
  // // from hidden field, set for selected child
  // String childId = request.getParameter("child_id");
  //
  // LOGGER.debug("childId: " + childId);
  // LOGGER.debug("fName: " + fName);
  // LOGGER.debug("lName: " + lName);
  // LOGGER.debug("address: " + address);
  // LOGGER.debug("dateOfBirth: " + dateOfBirth);
  // LOGGER.debug("userId: " + userId);
  //
  // Child child = new Child(Integer.parseInt(childId));
  // child.setFirstName(fName);
  // child.setLastName(lName);
  // child.setAddress(address);
  // child.setDateOfBirth(dateOfBirth);
  // child.setUser(new User(userId));
  //
  // try {
  // dataFacade.updateChild(child);
  //
  // destination = ADMIN_PAGE;
  // } catch (UserDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // }
  //
  // private void processNewNotes(HttpServletRequest request) {
  // Subject subject = (Subject) session.getAttribute("subject");
  // int userId = subject.getUserId();
  //
  // LOGGER.info("Create new notes for user ID: " + userId);
  //
  // // collect request parameters
  // String childIdStr = request.getParameter("child_id");
  // int childId = 0;
  // try {
  // childId = Integer.parseInt(childIdStr);
  // } catch (NumberFormatException e) {
  // LOGGER.error(e.getMessage());
  // request.setAttribute("Wrong child ID retrieved from HV notes", errMsg);
  // destination = ERROR_PAGE;
  // }
  // String dateOfVisitStr = request.getParameter("date_of_visit");
  // // NOTE: date format must be the same as specified in JQuery Datepicker
  // //DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
  // Date dateOfVisit = null;
  // if (dateOfVisitStr != null) {
  // try {
  // //dateOfVisit = (Date) dateFormatter.parse(dateOfVisitStr);
  // dateOfVisit = FRPUtil.convertStringToDate(dateOfVisitStr);
  // } catch (ParseException e) {
  // LOGGER.error(e.getMessage());
  // request.setAttribute("Wrong date format", errMsg);
  // destination = ERROR_PAGE;
  // }
  // }
  // String bookOrToy = request.getParameter("book_or_toy");
  // String bookOrToyReaction = request.getParameter("book_or_toy_reaction");
  // String present = request.getParameter("present");
  // String presentReaction = request.getParameter("present_reaction");
  // String presentParticipation = request.getParameter("present_participation");
  // String concerns = request.getParameter("concerns");
  // String questions = request.getParameter("questions");
  //
  // LOGGER.debug("userId: " + userId);
  // LOGGER.debug("childId: " + childId);
  // LOGGER.debug("dateOfVisit: " + dateOfVisit);
  // LOGGER.debug("dateOfVisitStr: " + dateOfVisitStr);
  // LOGGER.debug("bookOrToy: " + bookOrToy);
  // LOGGER.debug("bookOrToyReaction: " + bookOrToyReaction);
  // LOGGER.debug("present: " + present);
  // LOGGER.debug("presentReaction: " + presentReaction);
  // LOGGER.debug("presentParticipation: " + presentParticipation);
  // LOGGER.debug("concerns: " + concerns);
  // LOGGER.debug("questions: " + questions);
  //
  // try {
  // Note note = new Note(userId, childId);
  // note.setBookOrToy(bookOrToy);
  // note.setBookOrToyReaction(bookOrToyReaction);
  // note.setDateOfVisit(dateOfVisit);
  // note.setPresent(present);
  // note.setPresentReaction(presentReaction);
  // note.setPresentParticipation(presentParticipation);
  // note.setConcerns(concerns);
  // note.setQuestions(questions);
  //
  // dataFacade.createNote(note);
  // } catch (NoteDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  //
  // // try {
  // // // getServletConfig().getServletContext().getRequestDispatcher(
  // // // "/Controller?action=edit_child_page").forward(request, response);
  // // getServletConfig().getServletContext().getRequestDispatcher(
  // // "/Controller").forward(request, response);
  // // } catch (ServletException e) {
  // // // TODO Auto-generated catch block
  // // e.printStackTrace();
  // // } catch (IOException e) {
  // // // TODO Auto-generated catch block
  // // e.printStackTrace();
  // // }
  //
  // destination = NOTES_HOME_VISITOR_PAGE;
  // }
  //
  // private void processSearchNotes(HttpServletRequest request) {
  // LOGGER.info("Search notes");
  //
  // // collect request parameters
  // String userIdStr = request.getParameter("user_id");
  // if (userIdStr != null) {
  // try {
  // int userId = Integer.parseInt(userIdStr);
  // LOGGER.debug("userId: " + userId);
  //
  // // retrieve and put list into request for use in jsp
  // List<Note> notes = dataFacade.findNotesForUser(userId);
  // session.setAttribute("notes", notes);
  //
  // destination = NOTES_SUPERVISOR_PAGE;
  // } catch (NumberFormatException e) {
  // LOGGER.error(e.getMessage());
  // request.setAttribute("Wrong child ID retrieved from HV notes", errMsg);
  // destination = ERROR_PAGE;
  // } catch (NoteDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // } else {
  // LOGGER.error("Retrieved user ID not a numerical value");
  // request.setAttribute("errorMessage", "Unexpected error. Please contact system administrator");
  // destination = ERROR_PAGE;
  // }
  // }
  //
  // private void processGetNote(HttpServletRequest request, HttpServletResponse response)
  // throws IOException {
  // LOGGER.info("Search notes by id");
  //
  // int noteId = Integer.parseInt(request.getParameter("note_id"));
  //
  // // notes are stored in session
  // List<Note> notes = (List<Note>) session.getAttribute("notes");
  // if (notes == null) {
  // destination = LOGIN_PAGE;
  // } else {
  // String jsonNote = null;
  // for (Note note : notes) {
  // if (note.getId() == noteId) {
  // jsonNote = toJsonObject(note);
  //
  // LOGGER.debug(note);
  // LOGGER.debug(jsonNote);
  //
  // break;
  // }
  // }
  //
  // if (jsonNote != null) {
  // response.getWriter().write(jsonNote);
  // } else {
  // LOGGER.error("Note has not been found.");
  // request.setAttribute("errorMessage", "Note not found. Contact system administrator.");
  // destination = ERROR_PAGE;
  // }
  // }
  // }
  //
  // private Note findNoteInCache(int noteId) {
  // // sorted notes are stored in session
  // List<Note> notes = (List<Note>) session.getAttribute("notes");
  // if (notes == null) {
  // destination = LOGIN_PAGE;
  // } else {
  // for (Note note : notes) {
  // if (note.getId() == noteId) {
  // return note;
  // }
  // }
  // }
  //      
  // return null;
  // }
  //
  // private void processAdminPage() {
  // LOGGER.info("Send supervisor administration page");
  // destination = ADMIN_PAGE;
  // }
  //
  // private void processUserListPage(HttpServletRequest request) {
  // LOGGER.info("Send user list page");
  //      
  // try {
  // List<User> users = dataFacade.findAllUsers();
  // request.setAttribute("users", users);
  // } catch (UserDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  //      
  // destination = USER_LIST_PAGE;
  // }
  //
  // private void processDeleteUser(HttpServletRequest request) {
  // String userId = request.getParameter("user_id");
  // int id = Integer.parseInt(userId);
  //
  // LOGGER.info("Delete user with id: " + id);
  //
  // try {
  // dataFacade.deleteUser(id);
  // } catch (DAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  //
  // destination = ADMIN_PAGE;
  // }
  //
  // private void processChildListPage(HttpServletRequest request) {
  // LOGGER.info("Send child list page");
  //
  // try {
  // List<Child> children = dataFacade.findAllChildren();
  // request.setAttribute("children", children);
  // } catch (UserDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  //
  // destination = CHILD_LIST_PAGE;
  // }
  //
  // private void processUpdateChildPage(HttpServletRequest request) {
  // String childId = request.getParameter("child_id");
  // int id = Integer.parseInt(childId);
  //      
  // LOGGER.info("Send page for child with id: " + id);
  //      
  // try {
  // Child child = dataFacade.findChild(id);
  //         
  // request.setAttribute("child", child);
  // request.setAttribute("action", "update_child");
  // // for child update, put only belonging user to attribute (to avoid change of user)
  // List<User> users = new ArrayList<User>();
  // //users.add(child.getUser());
  // users = dataFacade.findAllUsers();
  // request.setAttribute("users", users);
  //         
  // destination = NEW_CHILD_PAGE;
  // } catch (DAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // }
  //
  // private void processDeleteChild(HttpServletRequest request) {
  // String childId = request.getParameter("child_id");
  // int id = Integer.parseInt(childId);
  //      
  // LOGGER.info("Delete child with id: " + id);
  //      
  // try {
  // dataFacade.deleteChild(id);
  // } catch (DAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  //
  // destination = ADMIN_PAGE;
  // }
  //
  // private void processNewFeedback(HttpServletRequest request) {
  // LOGGER.info("Create new feedback");
  //
  // // collect request parameters
  // String feedback = request.getParameter("feedback");
  // String noteIdStr = request.getParameter("selected_note_id");
  // int noteId = 0;
  // try {
  // noteId = Integer.parseInt(noteIdStr);
  // } catch (NumberFormatException e) {
  // LOGGER.error(e.getMessage());
  // request.setAttribute("Wrong note ID retrieved from notes viewer", errMsg);
  // destination = ERROR_PAGE;
  // }
  //
  // LOGGER.debug("noteId: " + noteId);
  // LOGGER.debug("feedback: " + feedback);
  //
  // try {
  // dataFacade.addFeedback(noteId, feedback);
  // } catch (NoteDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  //
  // Note note = findNoteInCache(noteId);
  // if(note != null) {
  // note.setFeedback(feedback);
  // LOGGER.debug("Updated note in cache: " + note);
  // }
  //      
  // // Subject subject = (Subject) session.getAttribute("subject");
  // // int userId = subject.getUserId();
  //
  // // try {
  // // // getServletConfig().getServletContext().getRequestDispatcher(
  // // // "/Controller?action=edit_child_page").forward(request, response);
  // // getServletConfig().getServletContext().getRequestDispatcher(
  // // "/Controller").forward(request, response);
  // // } catch (ServletException e) {
  // // // TODO Auto-generated catch block
  // // e.printStackTrace();
  // // } catch (IOException e) {
  // // // TODO Auto-generated catch block
  // // e.printStackTrace();
  // // }
  //
  // // retrieve all children of the current user
  // // TODO: put into a method
  // // try {
  // // List<Child> children = dataFacade.findChildren(subject.getUserId());
  // // LOGGER.debug("Retrieved children.size(): " + children.size());
  // // // put list into request for use in jsp
  // // request.setAttribute("children", children);
  // // } catch (UserDAOException e) {
  // // request.setAttribute("errorMessage", e.getMessage());
  // // destination = ERROR_PAGE;
  // // }
  //
  // destination = HOME_PAGE;
  // }
  //
  // private void processUpdateUserPage(HttpServletRequest request) {
  // String userId = request.getParameter("user_id");
  // int id = Integer.parseInt(userId);
  //
  // LOGGER.info("Send page for user with id: " + id);
  //
  // try {
  // User user = dataFacade.findUser(id);
  // List<Role> roles = dataFacade.findAllRoles();
  //
  // request.setAttribute("user", user);
  // request.setAttribute("roles", roles);
  // request.setAttribute("action", "update_user");
  //
  // destination = NEW_USER_PAGE;
  // } catch (DAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // }
  //
  // private void processUpdateUser(HttpServletRequest request) {
  // LOGGER.info("Update an existing user");
  //
  // // collect request parameters
  // String username = request.getParameter("username");
  // String password = request.getParameter("password");
  // String fName = request.getParameter("first_name");
  // String lName = request.getParameter("last_name");
  // String role = request.getParameter("role");
  // // from hidden field, set for selected user
  // String userId = request.getParameter("user_id");
  //
  // LOGGER.debug("userId: " + userId);
  // LOGGER.debug("username: " + username);
  // LOGGER.debug("first_name: " + fName);
  // LOGGER.debug("last_name: " + lName);
  // LOGGER.debug("roleId: " + role);
  //
  // // retrieve role id from db for use in relationship with user
  // try {
  // int roleId = dataFacade.findRoleByName(role);
  // User user = new User(username, password, fName, lName, roleId);
  // user.setId(Integer.parseInt(userId));
  //
  // dataFacade.updateUser(user);
  //         
  // destination = ADMIN_PAGE;
  // } catch (UserDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // }
  //
  // private void processFeedbackListPage() {
  // LOGGER.info("Send search feedbacks page");
  // destination = FEEDBACK_LIST_PAGE;
  // }
  //   
  // private void processSearchFeedbacks(HttpServletRequest request) {
  // LOGGER.info("Search feedbacks");
  //
  // // collect request parameters
  // String dateFromStr = request.getParameter("date_from");
  // String dateToStr = request.getParameter("date_to");
  // LOGGER.debug("dateFromStr: " + dateFromStr);
  // LOGGER.debug("dateToStr: " + dateToStr);
  //
  // Date dateFrom = null;
  // Date dateTo = null;
  // try {
  // if (dateFromStr != null) {
  // dateFrom = FRPUtil.convertStringToDate(dateFromStr);
  // }
  // if (dateToStr != null) {
  // dateTo = FRPUtil.convertStringToDate(dateToStr);
  // }
  // } catch (ParseException e) {
  // LOGGER.error(e.getMessage());
  // }
  // LOGGER.debug("dateFrom: " + dateFrom);
  // LOGGER.debug("dateTo: " + dateTo);
  //
  // if (dateFrom != null && dateTo != null) {
  // try {
  // // retrieve and put list into request for use in jsp
  // List<Note> notes = dataFacade.findNotesForFeedbacksInInterval(dateFrom, dateTo);
  // session.setAttribute("notes", notes);
  //
  // destination = FEEDBACK_LIST_PAGE;
  // } catch (NoteDAOException e) {
  // request.setAttribute("errorMessage", e.getMessage());
  // destination = ERROR_PAGE;
  // }
  // } else {
  // LOGGER.error("Problems with selected dates");
  // request.setAttribute("errorMessage", "Unexpected error. Please contact system administrator");
  // destination = ERROR_PAGE;
  // }
  // }
  //
  // /*
  // * Creates JSON return string to be displayed in HTML
  // */
  // private String toJsonObject(Note note) {
  // JSONObject jsonNote = new JSONObject();
  // JSONObject jsonNoteDetails = new JSONObject();
  //
  // jsonNoteDetails.put("id", note.getId());
  // jsonNoteDetails.put("userId", note.getUserId());
  // jsonNoteDetails.put("childId", note.getChildId());
  // jsonNoteDetails.put("bookOrToy", note.getBookOrToy());
  // jsonNoteDetails.put("bookOrToyReaction", note.getBookOrToyReaction());
  // jsonNoteDetails.put("dateOfVisit", note.getDateOfVisit().toString());
  // jsonNoteDetails.put("present", note.getPresent());
  // jsonNoteDetails.put("presentReaction", note.getPresentReaction());
  // jsonNoteDetails.put("presentParticipation", note.getPresentParticipation());
  // jsonNoteDetails.put("concerns", note.getConcerns());
  // jsonNoteDetails.put("questions", note.getQuestions());
  // jsonNoteDetails.put("feedback", note.getFeedback());
  // SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
  // Date feedbackDate = note.getFeedbackDate();
  // if(feedbackDate != null) {
  // jsonNoteDetails.put("feedbackDate", dateFormatter.format(feedbackDate));
  // } else {
  // jsonNoteDetails.put("feedbackDate", "");
  // }
  // jsonNoteDetails.put("childFirstName", note.getSpec().getChildFirstName());
  // jsonNoteDetails.put("childLastName", note.getSpec().getChildLastName());
  // jsonNoteDetails.put("userFirstName", note.getSpec().getUserFirstName());
  // jsonNoteDetails.put("userLastName", note.getSpec().getUserLastName());
  //
  // jsonNote.put("note", jsonNoteDetails);
  //
  // LOGGER.debug("-> JSON: " + jsonNote.toString());
  // return jsonNote.toString();
  // }

  /**
   * Closes all database connections. This method is invoked when the Servlet is unloaded.
   */
  public void destroy() {
    super.destroy();
    DBUtil.closeAllConnections();
  }
}