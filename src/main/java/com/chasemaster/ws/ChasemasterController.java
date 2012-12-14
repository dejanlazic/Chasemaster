package com.chasemaster.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.chasemaster.ws.data.User;

@WebService
public interface ChasemasterController {
   @WebMethod
   public Boolean register(@WebParam(name="user")User user);
   @WebMethod(operationName = "login")
   public String login();
}