package com.chasemaster.persistence;

import com.chasemaster.exception.GameException;
import com.chasemaster.persistence.db.DBConfig;

public abstract class GameDAO implements ChasemasterDAO {
   private static GameDAO instance;

   /**
    * @return The singleton instance of this class.
    */
   public static synchronized GameDAO getInstance() throws DAOException {
      if (instance == null) {
         String gameDaoClassName = DBConfig.getGameDaoClassName();
         
         try {
            Class gameDaoClass = Class.forName(gameDaoClassName);
            instance = (GameDAO) gameDaoClass.newInstance();
         } catch (Exception ex) {
            throw new DAOException("Unable to instantiate " + gameDaoClassName);
         }
      }
      return instance;
   }

   public abstract void performMovement() throws GameException;
}