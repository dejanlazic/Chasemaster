package com.chasemaster.ws.service;

//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;

import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.chasemaster.ws.data.User;

//@Service("chasemasterService")
public final class ChasemasterServiceImpl implements ChasemasterService {
   public Boolean register(User user) {
     System.out.println("In ChasemasterService: " + user.getUsername() + ", " + user.getPassword() + ", " + user.getEmail());;
     return true;
   }

   public String login() {
      throw new java.lang.UnsupportedOperationException("Not implemented yet");
   }
}