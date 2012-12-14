package com.chasemaster.ws.service;

import com.chasemaster.ws.data.User;

public interface ChasemasterService {
   public Boolean register(User user);
   public String login();
}