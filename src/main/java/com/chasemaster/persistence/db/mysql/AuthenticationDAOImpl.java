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
import com.chasemaster.Colour;

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
    final String sqlSel = "SELECT MAX(id) AS max_match_id FROM matches";
    final String sql = "SELECT * FROM players WHERE username=? AND password=? AND match_id=?";
    Player player = null;

    Connection con = null;
    PreparedStatement stmt = null;

    try {
      con = DBUtil.getConnection();

      // read the latest match id
      stmt = con.prepareStatement(sqlSel);
      int maxMatchId = -1;
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        rs.next(); // there is only value selected, loop unnecessary
        maxMatchId = rs.getInt("max_match_id");
        LOGGER.info("(DB)--> Largest match id selected: maxMatchId=" + maxMatchId);
      }

      stmt = con.prepareStatement(sql);
      stmt.setString(1, username);
      stmt.setString(2, password);
      stmt.setInt(3, maxMatchId);

      rs = stmt.executeQuery();
      if (rs != null) {
        // check if result set is empty or contains multiple records
        rs.beforeFirst();
        rs.last();
        int size = rs.getRow();
        LOGGER.debug("(DB)--> ResultSet size: " + size);
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
        String uname = rs.getString("username");
        // String pwd = rs.getString("password");
        String colour = rs.getString("colour");

        player = new Player(id, uname, Colour.forString(colour));

        LOGGER.info("(DB)--> User authenticated: " + player);
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