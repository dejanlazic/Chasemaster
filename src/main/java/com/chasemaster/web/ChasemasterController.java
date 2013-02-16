package com.chasemaster.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class ChasemasterController {
  private final static Logger logger = Logger.getLogger(ChasemasterController.class);

//  private ChasemasterService chasemasterService;
//
//  private List<Map<String, String>> dataList = null;
//  private Map<String, String> geoDataMap = null;
//  private UserCredentials userCredentials = new UserCredentials();
//
//  @RequestMapping("/")
//  public String startup(Map<String, Object> map) {
//    map.put("userCredentials", userCredentials);
//    Map<String, String> days = new LinkedHashMap<String, String>();
//    days.put("1", "1");
//    days.put("2", "2");
//    days.put("3", "3");
//    days.put("4", "4");
//    days.put("5", "5");
//    map.put("daysList", days);
//    map.put("dataList", dataList);
//    map.put("geoDataMap", geoDataMap);
//
//    return "login";
//  }
//
//  @RequestMapping(value = "/registrationForm", method = RequestMethod.GET)
//  public String sendRegistrationForm(Map<String, Object> map) {
//    logger.debug("In sendRegistrationForm()");
//    
//    User user = new User();
////    user.setId(1L);
////    user.setUsername("2");
////    user.setPassword("3");
////    user.setPasswordConfirmation("4");
////    user.setEmail("5");
//    map.put("user", user);
//
//    return "registration";
//  }
//  
//  @RequestMapping(value = "/register", method = RequestMethod.POST)
//  public String register(@ModelAttribute("user") User user, BindingResult result) {
//    logger.debug("In register(): " + user);
//
//    String retval = chasemasterService.register(user);
//    logger.debug("In register(): " + retval);
//    
//    return "redirect:/web/";
//  }
//  
//  @RequestMapping(value = "/login", method = RequestMethod.POST)
//  public String login(@ModelAttribute("userCredentials") UserCredentials userCredentials, BindingResult result) {
//    logger.debug("In login(): " + userCredentials);
//
//    String retval = chasemasterService.login(userCredentials);
//    logger.debug("In login(): " + retval);
//    
//    ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
//    HttpSession session = attributes.getRequest().getSession();
//    session.setAttribute("userId", retval);
//    
//    // if(success)
//    return "game";
//  }
//
//  @RequestMapping(value = "/move", method = RequestMethod.GET)
//  public String performMovement() {
//    logger.debug("In performMovement()");
//    System.out.println("In performMovement()");
//
//    //String retval = chasemasterService.login(userCredentials);
//
////    String move1 = request.getParameter("move1");
////    String move2 = request.getParameter("move2");
//
////    System.out.println("[" + move1 + ", " + move2 + "]");
//
//    // setup the response
////    response.setContentType("text/xml");
////    response.setHeader("Cache-Control", "no-cache");
////    response.getWriter().write(move1 + ", " + move2);
//    
//    //return "redirect:/web/";
//    return "game";
//  }
}