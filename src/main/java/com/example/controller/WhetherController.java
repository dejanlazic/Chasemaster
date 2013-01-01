package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.User;
import com.example.model.UserCredentials;
import com.example.service.WhetherService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

@Controller
public class WhetherController {
  private final static Logger logger = Logger.getLogger(WhetherController.class);

  @Autowired
  private WhetherService whetherService;

  private List<Map<String, String>> dataList = null;
  private Map<String, String> geoDataMap = null;
  private UserCredentials userCredentials = new UserCredentials();

  @RequestMapping("/")
  public String startup(Map<String, Object> map) {
    map.put("userCredentials", userCredentials);
    Map<String, String> days = new LinkedHashMap<String, String>();
    days.put("1", "1");
    days.put("2", "2");
    days.put("3", "3");
    days.put("4", "4");
    days.put("5", "5");
    map.put("daysList", days);
    map.put("dataList", dataList);
    map.put("geoDataMap", geoDataMap);

    return "login";
  }

  @RequestMapping(value = "/registrationForm", method = RequestMethod.GET)
  public String sendRegistrationForm(Map<String, Object> map) {
    logger.debug("In sendRegistrationForm()");
    
    User user = new User();
//    user.setId(1L);
//    user.setUsername("2");
//    user.setPassword("3");
//    user.setPasswordConfirmation("4");
//    user.setEmail("5");
    map.put("user", user);

    return "registration";
  }
  
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String register(@ModelAttribute("user") User user, BindingResult result) {
    logger.debug("In register(): " + user);

    String retval = whetherService.register(user);
    logger.debug("In register(): " + retval);
    
    return "redirect:/Chasemaster/";
  }
  
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(@ModelAttribute("userCredentials") UserCredentials userCredentials, BindingResult result) {
    logger.debug("In login(): " + userCredentials);
    //userCredentials = new UserCredentials();

    String retval = whetherService.login(userCredentials);
    logger.debug("In login(): " + retval);

    return "redirect:/Chasemaster/";
  }
}