package com.chasemaster.ws.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chasemaster.ws.ChasemasterController;
import com.chasemaster.ws.data.User;

public final class Client {
   public static void main(String args[]) throws Exception {
      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext (
            new String[] { "com/chasemaster/ws/test/appContext-client.xml" });

      ChasemasterController cmClient = (ChasemasterController) context.getBean("chasemasterClient");
      
      User user = new User();
      user.setUsername("John");
      user.setPassword("Doe");
      user.setEmail("John.Doe");

      boolean retval = cmClient.register(user);
      System.out.println("retval: " + retval);
   }
}