package com.chasemaster.persistence.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.chasemaster.exception.MatchException;
import com.chasemaster.exception.MovementException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.MovementDAO;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.movement.Movement;

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
  
  @Override
  public List<Movement> readAll(int turnId) throws MovementException {
    final String sql = "SELECT * FROM movements WHERE turn_id=? ORDER BY player_id";

    Connection con = null;
    PreparedStatement stmt = null;

    List<Movement> movements = new ArrayList<Movement>();
    try {
      con = DBUtil.getConnection();
      stmt = con.prepareStatement(sql);
      stmt.setInt(1, turnId);

      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          Movement movement = new Movement(rs.getInt("id"));
          movement.setPlayerId(Integer.toString(rs.getInt("player_id")));
          movement.setFrom(Location.forString(rs.getString("location_from")));
          movement.setTo(Location.forString(rs.getString("location_to")));
          movement.setDuration(new Long(rs.getInt("duration")));          
          
          movements.add(movement);
        }
      }

      LOGGER.info("(DB)--> Movements selected: " + movements);

      rs.close();
      // con.commit();
    } catch (SQLException sqe) {
      // try {
      // con.rollback();
      // } catch (SQLException e) {
      // e.printStackTrace();
      // }
      throw new MovementException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }

    return movements;
  }
}