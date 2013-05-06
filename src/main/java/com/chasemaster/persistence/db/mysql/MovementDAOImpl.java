package com.chasemaster.persistence.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.chasemaster.exception.MatchException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.MovementDAO;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;

/**
 * An implementation of the Movement DAO that uses MySQL JDBC.
 */
public class MovementDAOImpl extends MovementDAO {
  private final Logger LOGGER = Logger.getLogger(MovementDAOImpl.class);

  /**
   * Construct the data adapter and load the JDBC driver.
   */
  public MovementDAOImpl() throws DAOException {
    try {
      Class.forName(DBConfig.getJDBCDriverClassName());
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new DAOException("Unable to load JDBC driver: " + DBConfig.getJDBCDriverClassName());
    }
  }

  @Override
  public void create(int turnId, int playerId, String locationFrom, String locationTo, long duration) throws MatchException {
    final String sql = "INSERT INTO movements (turn_id, player_id, location_from, location_to, duration) VALUES (?,?,?,?,?)";

    Connection con = null;
    PreparedStatement stmt = null;

    try {
      con = DBUtil.getConnection();
      stmt = con.prepareStatement(sql);

      stmt.setInt(1, turnId);
      stmt.setInt(2, playerId);
      stmt.setString(3, locationFrom);
      stmt.setString(4, locationTo);
      stmt.setLong(5, duration);

      stmt.executeUpdate();

      LOGGER.info("(DB) --> Movement created: turnId=" + turnId + ", playerId=" + playerId + ", locationFrom=" + locationFrom + ", locationTo=" + locationTo+ ", duration=" + duration);

      con.commit();
    } catch (SQLException sqe) {
      try {
        con.rollback();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      throw new MatchException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }
  }
}