package com.chasemaster.util;

public interface PageConst {
  String PREFIX = "/WEB-INF/view/";
  String SUFFIX = ".jsp";

  String ERROR_PAGE         = PREFIX    + "error"          + SUFFIX;  
  String LOGIN_PAGE         = PREFIX    + "login"          + SUFFIX;  
  String REGISTRATION_PAGE  = PREFIX    + "registration"   + SUFFIX;
  String ADMIN_PAGE         = PREFIX    + "admin"          + SUFFIX;
  String GAME_PAGE          = PREFIX    + "game"           + SUFFIX;
  String HOME_PAGE = "/home.jsp";
  
  
  
  String D_N_D          = PREFIX    + "draganddrop"            + SUFFIX; // TODO Remove
}