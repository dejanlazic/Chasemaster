package com.chasemaster.persistence;

import com.chasemaster.exception.MatchException;
import com.chasemaster.persistence.db.DBConfig;

public abstract class MovementDAO implements ChasemasterDAO {
   private static MovementDAO instance;

   /**
    * @return The singleton instance of this class.
    */
   public static synchronized MovementDAO getInstance() throws DAOException {
      if (instance == null) {
         String movementDaoClassName = DBConfig.getMovementDaoClassName();
         
         try {
            Class movementDaoClass = Class.forName(movementDaoClassName);
            instance = (MovementDAO) movementDaoClass.newInstance();
         } catch (Exception ex) {
            throw new DAOException("Unable to instantiate " + movementDaoClassName);
         }
      }
      return instance;
   }

   public abstract void create(int turnId, int playerId, String locationFrom, String locationTo, long duration) throws MatchException;
}