package com.example.service;

import com.example.model.User;
import com.example.model.UserCredentials;

public interface WhetherService {
  public String register(User user);
  public String login(UserCredentials userCredentials);
}