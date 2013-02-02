package com.chasemaster.ws.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
//import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
//import javax.persistence.Query;

//import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chasemaster.ws.Response;
import com.chasemaster.ws.data.User;
import com.chasemaster.ws.data.UserCredentials;
//import com.chasemaster.ws.util.EmailSender;

//@Service("chasemasterService")
public final class ChasemasterServiceImpl implements ChasemasterService {
   @PersistenceContext
   private EntityManager entityManager;

   @Transactional
   public String register(User user) {
      if (user == null) {
         throw new RuntimeException("User cannot be null or empty");
      }

      System.out.println("In ChasemasterService#register: " + user);
      
      if(!user.getPassword().equalsIgnoreCase(user.getPasswordConfirmation())) {
         return "ERROR: Passwords are not the same";
      }

      String message = null;
      User u = null;
      try {
         if (entityManager == null) {
            System.out.println("entityManager is null");
            return "SYSTEM ERROR: entityManager is null";
         }
         
         Query q = entityManager.createQuery("FROM User u WHERE u.username = :username");
         q.setParameter("username", user.getUsername());
         u = (User) q.getSingleResult();
         
         if(u != null) System.out.println("Found: " + u);
         message = "ERROR: User already exists";
      } catch (NoResultException e) {
         u = new User();
         u.setUsername(user.getUsername());
         u.setPassword(user.getPassword());
         u.setEmail(user.getEmail());
         u.setRegisteredOn(new Date());
         
         entityManager.persist(u);
         System.out.println("Created new: " + u);
         message = "User successfully registered";
      }

//      EmailSender sender = new EmailSender();
//      sender.send(u.getEmail(), "Email text");
      
      return message;
   }

   @Transactional
   public Response login(UserCredentials userCredentials) {
      if (userCredentials == null) {
         throw new RuntimeException("User credentials cannot be null or empty");
      }

      System.out.println("In ChasemasterService#login: " + userCredentials);

      Response response = new Response();
      try {
         if (entityManager == null) {
            System.out.println("entityManager is null");
            return new Response("entityManager is null", "SYSTEM ERROR");
         }
         
         // check if username and password are the same as passed
         Query q = entityManager.createQuery("FROM User u WHERE u.username = :username");
         q.setParameter("username", userCredentials.getUsername());
         User u = (User) q.getSingleResult();
         System.out.println("Selected user: " + u);
         
         if(userCredentials.getPassword().equalsIgnoreCase(u.getPassword())) {
            response.setMessageText(u.getId().toString());
            response.setMessageType("SUCCESS");
         } else {         
            response.setMessageText("Incorrect password");
            response.setMessageType("ERROR");
         }
      } catch (NoResultException e) {
         response.setMessageText("User does not exist");
         response.setMessageType("ERROR");
      }

//      EmailSender sender = new EmailSender();
//      sender.send(u.getEmail(), "Email text");
      
      return response;
   }
}