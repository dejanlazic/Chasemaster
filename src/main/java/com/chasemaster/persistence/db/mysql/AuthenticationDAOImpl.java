package com.chasemaster.persistence.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.chasemaster.exception.NoResultException;
import com.chasemaster.exception.NoUniqueResultException;
import com.chasemaster.persistence.AuthenticationDAO;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;
import com.chasemaster.persistence.model.Player;

/**
 * An implementation of the UserDAO that uses MySQL JDBC.
 */
public class AuthenticationDAOImpl extends AuthenticationDAO {
  private final Logger LOGGER = Logger.getLogger(AuthenticationDAOImpl.class);

  /**
   * Construct the data adapter and load the JDBC driver.
   */
  public AuthenticationDAOImpl() throws DAOException {
    try {
      Class.forName(DBConfig.getJDBCDriverClassName());
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new DAOException("Unable to load JDBC driver: " + DBConfig.getJDBCDriverClassName());
    }
  }

  @Override
  public Player authenticate(String username, String password) throws DAOException {
    final String sql = "SELECT * FROM players WHERE username=? AND password=?";
    Player player = null;

    Connection con = null;
    PreparedStatement stmt = null;
    try {
      con = DBUtil.getConnection();
      stmt = con.prepareStatement(sql);

      stmt.setString(1, username);
      stmt.setString(2, password);

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
        String pwd = rs.getString("password");
        LOGGER.info("Selected user: [id=" + id + ", uName=" + uName + ", pwd=" + pwd+ "]");
        
        player = new Player();
        player.setId(id);
        player.setUsername(uName);
      }
      con.commit();

      return player;
    } catch (SQLException sqe) {
      sqe.printStackTrace();
      throw new DAOException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }
  }
}