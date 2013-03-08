package com.chasemaster.persistence;

import com.chasemaster.exception.NoResultException;
import com.chasemaster.exception.NoUniqueResultException;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.model.Player;

public abstract class PlayerDAO implements ChasemasterDAO {
   private static PlayerDAO instance;

   /**
    * @return The singleton instance of this class.
    */
   @SuppressWarnings("unchecked")
  public static synchronized PlayerDAO getInstance() throws DAOException {
      if (instance == null) {
         String playerDaoClassName = DBConfig.getPlayerDaoClassName();
         
         try {
            Class playerDaoClass = Class.forName(playerDaoClassName);
            instance = (PlayerDAO) playerDaoClass.newInstance();
         } catch (Exception ex) {
            throw new DAOException("Unable to instantiate " + playerDaoClassName);
         }
      }
      return instance;
   }

   public abstract void create(String username, String password, String colour) throws DAOException;
   public abstract Player find(String username) throws NoResultException, NoUniqueResultException, DAOException;
}