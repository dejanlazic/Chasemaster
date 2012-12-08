package com.chasemaster.ws;

import javax.jws.WebService;

import com.chasemaster.ws.data.User;


//@WebService(name="ChasemasterService",
//         portName = "ChasemasterPort",
//         endpointInterface = "cm.ChasemasterService",
//         wsdlLocation = "webapp/WEB-INF/wsdl/ChasemasterService.wsdl",
//         targetNamespace="http://localhost:8080/chasemaster")
@WebService(endpointInterface = "com.chasemaster.ws.ChasemasterService")         
public final class ChasemasterServiceImpl implements ChasemasterService {
   public boolean register(User user) {
     System.out.println(user.getUsername() + ", " + user.getPassword() + ", " + user.getEmail());;
     return true;
   }

   public String login() {
      throw new java.lang.UnsupportedOperationException("Not implemented yet");
   }
}