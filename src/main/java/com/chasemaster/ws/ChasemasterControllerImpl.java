package com.chasemaster.ws;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chasemaster.ws.data.User;
import com.chasemaster.ws.data.UserCredentials;
import com.chasemaster.ws.service.ChasemasterService;

//@WebService(name="ChasemasterService",
//portName = "ChasemasterPort",
//endpointInterface = "cm.ChasemasterService",
//wsdlLocation = "webapp/WEB-INF/wsdl/ChasemasterService.wsdl",
//targetNamespace="http://localhost:8080/chasemaster")      
@WebService(endpointInterface = "com.chasemaster.ws.ChasemasterController")
//@WebService(name = "/chasemaster", endpointInterface = "com.chasemaster.ws.ChasemasterController")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
public class ChasemasterControllerImpl implements ChasemasterController {
//  @Autowired
//  @Qualifier("chasemasterService")
  private ChasemasterService chasemasterService;
  
  public void setChasemasterService(ChasemasterService chasemasterService) {
     this.chasemasterService = chasemasterService;
  }
  
  public String register(User user) {
     System.out.println("In ChasemasterController#register: " + user);
     
     if(chasemasterService == null) {
        System.out.println("In ChasemasterController: chasemasterService is null");
        return "SYSTEM ERROR: chasemasterService is null"; // TODO: Return some Fault object
     }
     
     return chasemasterService.register(user);
   }

   public Response login(UserCredentials userCredentials) {
      System.out.println("In ChasemasterController#login: " + userCredentials);
      
      if(chasemasterService == null) {
         System.out.println("In ChasemasterController: chasemasterService is null");
         return new Response("chasemasterService is null", "SYSTEM ERROR"); // TODO: Return some Fault object
      }
      
      return chasemasterService.login(userCredentials);
   }   
}