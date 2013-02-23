package com.chasemaster.persistence.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.chasemaster.exception.NoResultException;
import com.chasemaster.exception.NoUniqueResultException;
import com.chasemaster.exception.PlayerException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.PlayerDAO;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;
import com.chasemaster.persistence.model.Player;

/**
 * An implementation of the UserDAO that uses MySQL JDBC.
 */
public class PlayerDAOImpl extends PlayerDAO {
  private final Logger LOGGER = Logger.getLogger(PlayerDAOImpl.class);

  /**
   * Construct the data adapter and load the JDBC driver.
   */
  public PlayerDAOImpl() throws DAOException {
    try {
      Class.forName(DBConfig.getJDBCDriverClassName());
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new DAOException("Unable to load JDBC driver: " + DBConfig.getJDBCDriverClassName());
    }
  }

  @Override
  public void create(String username, String password) throws DAOException {
    final String sql = "INSERT INTO players (username, password, registered_on)"
        + " VALUES (?, ?, ?)";

    Connection con = null;
    PreparedStatement stmt = null;
    
    try {
      con = DBUtil.getConnection();
      stmt = con.prepareStatement(sql);

      stmt.setString(1, username);
      stmt.setString(2, password);
      // convert Java Date to SQL Date
      java.util.Date currDate = new Date();
      java.sql.Date sqlcurrDate = new java.sql.Date(currDate.getTime());
      stmt.setDate(3, sqlcurrDate);

      stmt.executeUpdate();

      LOGGER.info("USER CREATED -> " + username);

      con.commit();
    } catch (SQLException sqe) {
      try {
        con.rollback();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      throw new DAOException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }
  }

  /*
   * Retrieve a single (the first found) entity
   * 
   * @see com.chasemaster.persistence.PlayerDAO#find(java.lang.String)
   */
  @Override
  public Player find(String username) throws NoResultException, NoUniqueResultException, DAOException {
    final String sql = "SELECT * FROM players WHERE username=?";

    Connection con = null;
    PreparedStatement stmt = null;

    try {
      con = DBUtil.getConnection();
      stmt = con.prepareStatement(sql);

      Player player = null;

      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        // check if result set is empty or contains multiple records
        rs.beforeFirst();
        rs.last();
        int size = rs.getRow();
        LOGGER.info("ResultSet size: " + size);
        if (size == 0) {
          throw new NoResultException();
        }
        if (size > 1) {
          throw new NoUniqueResultException("Found multiple players with the same username " + username);
        }

        // parse result set
        rs.beforeFirst();
        rs.next();

        int id = rs.getInt("id");
        String uName = rs.getString("username");
        String fName = rs.getString("first_name");
        String lName = rs.getString("last_name");

        player = new Player(id, uName, fName, lName);

        LOGGER.info("Selected Player[id=" + id + ", fName=" + fName + ", lName=" + lName + "]");
      }
      con.commit();

      return player;
    } catch (SQLException sqe) {
      try {
        con.rollback();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      throw new DAOException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }
  }
}