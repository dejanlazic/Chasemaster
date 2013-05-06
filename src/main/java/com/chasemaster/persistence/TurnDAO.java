package com.chasemaster.persistence;

import com.chasemaster.exception.TurnException;
import com.chasemaster.persistence.db.DBConfig;

public abstract class TurnDAO implements ChasemasterDAO {
   private static TurnDAO instance;

   /**
    * @return The singleton instance of this class.
    */
   public static synchronized TurnDAO getInstance() throws DAOException {
      if (instance == null) {
         String turnDaoClassName = DBConfig.getTurnDaoClassName();
         
         try {
            Class turnDaoClass = Class.forName(turnDaoClassName);
            instance = (TurnDAO) turnDaoClass.newInstance();
         } catch (Exception ex) {
            throw new DAOException("Unable to instantiate " + turnDaoClassName);
         }
      }
      return instance;
   }

   public abstract void create(int matchId, String colour, String winners) throws TurnException;
   public abstract int readMaxId() throws TurnException;
}