package com.chasemaster.web.service;

import com.chasemaster.web.model.User;
import com.chasemaster.web.model.UserCredentials;

public interface ChasemasterService {
  public String register(User user);
  public String login(UserCredentials userCredentials);
}