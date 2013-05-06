package com.chasemaster.persistence;

import java.util.Date;

import com.chasemaster.exception.MatchException;
import com.chasemaster.persistence.db.DBConfig;

public abstract class MatchDAO implements ChasemasterDAO {
   private static MatchDAO instance;

   /**
    * @return The singleton instance of this class.
    */
   public static synchronized MatchDAO getInstance() throws DAOException {
      if (instance == null) {
         String matchDaoClassName = DBConfig.getMatchDaoClassName();
         
         try {
            Class matchDaoClass = Class.forName(matchDaoClassName);
            instance = (MatchDAO) matchDaoClass.newInstance();
         } catch (Exception ex) {
            throw new DAOException("Unable to instantiate " + matchDaoClassName);
         }
      }
      return instance;
   }

   public abstract void create(Date playOn) throws MatchException;
   public abstract int readMaxId() throws MatchException;
}