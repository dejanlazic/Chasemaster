package com.chasemaster.persistence.db;

public class DBConfig {
   // maximum sizes of various fields in the database
   public static final int MAX_BOARD_NAME_LEN = 80;

   private static String jdbcDriverClassName;
   private static String databaseURL;
   private static String databaseUsername;
   private static String databasePassword;
   
   private static String authDaoClassName;
   private static String playerDaoClassName;
   private static String gameDaoClassName;

   /*
    * Private constructor prevents instantiating of the object
    * (therefore, to use static methods)
    */
   private DBConfig() {
   }

   // TODO: Use array of DAOs as the last attribute
   public static void setValues(String jdbcDriverClassName, String databaseURL, String databaseUsername,
         String databasePassword, String authDaoClassName, String playerDaoClassName, String gameDaoClassName) {
      DBConfig.jdbcDriverClassName = jdbcDriverClassName;
      DBConfig.databaseURL = databaseURL;
      DBConfig.databaseUsername = databaseUsername;
      DBConfig.databasePassword = databasePassword;

      DBConfig.authDaoClassName = authDaoClassName;
      DBConfig.playerDaoClassName = playerDaoClassName;
      DBConfig.gameDaoClassName = gameDaoClassName;
   }

   /**
    * @return The JDBC driver class name.
    */
   public static String getJDBCDriverClassName() {
      return DBConfig.jdbcDriverClassName;
   }

   /**
    * @return The JDBC database URL.
    */
   public static String getDatabaseURL() {
      return DBConfig.databaseURL;
   }

   /**
    * @return The database username.
    */   
   public static String getDatabaseUsername() {
      return databaseUsername;
   }

   /**
    * @return The database password.
    */   
   public static String getDatabasePassword() {
      return databasePassword;
   }

   /**
    * @return The login DAO implementation class name.
    */
   public static String getAuthDaoClassName() {
      return DBConfig.authDaoClassName;
   }

   /**
    * @return The login DAO implementation class name.
    */
   public static String getPlayerDaoClassName() {
      return DBConfig.playerDaoClassName;
   }

   /**
    * @return The user DAO implementation class name.
    */
   public static String getGameDaoClassName() {
      return DBConfig.gameDaoClassName;
   }
}