package com.chasemaster.ws.service;

import com.chasemaster.ws.Response;
import com.chasemaster.ws.data.User;
import com.chasemaster.ws.data.UserCredentials;

public interface ChasemasterService {
   public String register(User user);
   public Response login(UserCredentials userCredentials);
}