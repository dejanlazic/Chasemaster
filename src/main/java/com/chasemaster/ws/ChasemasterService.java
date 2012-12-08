package com.chasemaster.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.chasemaster.ws.data.User;

@WebService
public interface ChasemasterService {
   @WebMethod
   public boolean register(@WebParam(name="user")User user);
   @WebMethod
   public String login();
}