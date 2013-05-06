package com.chasemaster.persistence.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.chasemaster.exception.TurnException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.TurnDAO;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;

/**
 * An implementation of the TurnDAO that uses MySQL JDBC.
 */
public class TurnDAOImpl extends TurnDAO {
  private final Logger LOGGER = Logger.getLogger(TurnDAOImpl.class);

  /**
   * Construct the data adapter and load the JDBC driver.
   */
  public TurnDAOImpl() throws DAOException {
    try {
      Class.forName(DBConfig.getJDBCDriverClassName());
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new DAOException("Unable to load JDBC driver: " + DBConfig.getJDBCDriverClassName());
    }
  }

  @Override
  public void create(int matchId, String colour, String winners) throws TurnException {
    final String sql = "INSERT INTO turns (match_id, colour, winners) VALUES (?,?,?)";

    Connection con = null;
    PreparedStatement stmt = null;

    try {
      con = DBUtil.getConnection();
      stmt = con.prepareStatement(sql);

      stmt.setInt(1, matchId);
      stmt.setString(2, colour);
      stmt.setString(3, winners);

      stmt.executeUpdate();

      LOGGER.info("(DB) --> Turn created: matchId=" + matchId + ", colour=" + colour + ", winners=" + winners);

      con.commit();
    } catch (SQLException sqe) {
      try {
        con.rollback();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      throw new TurnException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }
  }

  @Override
  public int readMaxId() throws TurnException {
    final String sql = "SELECT MAX(id) AS max_id FROM turns";

    Connection con = null;
    Statement stmt = null;

    int maxId = 0;
    try {
      con = DBUtil.getConnection();
      stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery(sql);
      if (rs != null) {
        rs.next(); // once is enough
        maxId = rs.getInt("max_id");
      }

      LOGGER.info("(DB) --> Turn ID selected: max_id=" + maxId);

      rs.close();
      // con.commit();
    } catch (SQLException sqe) {
      // try {
      // con.rollback();
      // } catch (SQLException e) {
      // e.printStackTrace();
      // }
      throw new TurnException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }

    return maxId;
  }
}