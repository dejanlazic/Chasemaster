package com.chasemaster.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.chasemaster.ws.data.User;
import com.chasemaster.ws.data.UserCredentials;

@WebService
public interface ChasemasterController {
   @WebMethod
   public String register(@WebParam(name="User")User user);
   @WebMethod(operationName="login")
   public Response login(@WebParam(name="UserCredentials")UserCredentials userCredentials);
}