package com.chasemaster.ws.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
//import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
//import javax.persistence.Query;

//import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chasemaster.ws.data.User;

//@Service("chasemasterService")
public final class ChasemasterServiceImpl implements ChasemasterService {
   @PersistenceContext
   private EntityManager entityManager;

   @Transactional
   public Boolean register(User user) {
      if (user == null) {
         throw new RuntimeException("User cannot be null or empty");
      }

      System.out.println("In ChasemasterService: " + user.getUsername() + ", " + user.getPassword() + ", " + user.getEmail());

      User u = null;
      try {
         if (entityManager == null) {
            System.out.println("entityManager is null");
            return false;
         }
         
         Query q = entityManager.createQuery("FROM User u WHERE u.username = :username");
         q.setParameter("username", user.getUsername());
         u = (User) q.getSingleResult();
         
         if(u != null) System.out.println("Found: " + u);
      } catch (NoResultException e) {
         u = new User();
         u.setUsername(user.getUsername());
         u.setPassword("GENERATED");
         u.setEmail(user.getEmail());
         
         entityManager.persist(u);
         System.out.println("Created new: " + u);
      }

      return true;
   }

   public String login() {
      throw new java.lang.UnsupportedOperationException("Not implemented yet");
   }
}