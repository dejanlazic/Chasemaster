package com.chasemaster.persistence.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.chasemaster.exception.TurnException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.TurnDAO;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;
import com.chasemaster.persistence.model.Turn;

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

      LOGGER.info("(DB)--> Turn created: matchId=" + matchId + ", colour=" + colour + ", winners=" + winners);

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
  public List<Turn> readAll(int matchId) throws TurnException {
    final String sql = "SELECT * FROM turns WHERE match_id=? ORDER BY id";

    Connection con = null;
    PreparedStatement stmt = null;

    List<Turn> turns = new ArrayList<Turn>();
    try {
      con = DBUtil.getConnection();
      stmt = con.prepareStatement(sql);
      stmt.setInt(1, matchId);

      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          Turn turn = new Turn(rs.getInt("id"));
          turn.setColour(rs.getString("colour"));
          String winnersConcat = rs.getString("winners");
          String[] winnersArr = winnersConcat.split("|");
          List<Integer> winners = new ArrayList<Integer>();
          for(String w : winnersArr) {
            if(w != null && !w.equals("") && !w.equals("|")) 
              winners.add(Integer.parseInt(w));
          }
          turn.setWinners(winners);
          
          turns.add(turn);
        }
      }

      LOGGER.info("(DB)--> Turns selected: " + turns);

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

    return turns;
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

      LOGGER.info("(DB)--> Turn ID selected: max_id=" + maxId);

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