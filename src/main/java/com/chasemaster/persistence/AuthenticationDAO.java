package com.chasemaster.persistence;

import com.chasemaster.exception.LoginException;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.model.Player;

public abstract class AuthenticationDAO implements ChasemasterDAO {
   private static AuthenticationDAO instance;

   /**
    * @return The singleton instance of this class.
    */
   public static synchronized AuthenticationDAO getInstance() throws DAOException {
      if (instance == null) {
         String authDaoClassName = DBConfig.getAuthDaoClassName();
         
         try {
            Class authDaoClass = Class.forName(authDaoClassName);
            instance = (AuthenticationDAO) authDaoClass.newInstance();
         } catch (Exception ex) {
            throw new DAOException("Unable to instantiate " + authDaoClassName);
         }
      }
      return instance;
   }

   /**
    * @param username
    *           A valid user name
    * @param password
    *           A valid password
    * @return The subject containing user's identification information
    * @throws LoginException
    *            If authentication unsuccessful or a database error occurs
    */
   public abstract Player authenticate(String username, String password) throws DAOException;
}