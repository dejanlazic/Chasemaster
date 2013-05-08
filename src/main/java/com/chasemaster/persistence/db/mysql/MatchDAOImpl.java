package com.chasemaster.persistence.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.chasemaster.exception.MatchException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.MatchDAO;
import com.chasemaster.persistence.db.DBConfig;
import com.chasemaster.persistence.db.DBUtil;
import com.chasemaster.persistence.model.Match;
import com.chasemaster.service.ServiceException;

/**
 * An implementation of the MatchDAO that uses MySQL JDBC.
 */
public class MatchDAOImpl extends MatchDAO {
  private final Logger LOGGER = Logger.getLogger(MatchDAOImpl.class);

  /**
   * Construct the data adapter and load the JDBC driver.
   */
  public MatchDAOImpl() throws DAOException {
    try {
      Class.forName(DBConfig.getJDBCDriverClassName());
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new DAOException("Unable to load JDBC driver: " + DBConfig.getJDBCDriverClassName());
    }
  }

  @Override
  public void create(Date playOn) throws MatchException {
    final String sql = "INSERT INTO matches (play_on) VALUES (?)";

    Connection con = null;
    PreparedStatement stmt = null;

    try {
      con = DBUtil.getConnection();
      stmt = con.prepareStatement(sql);

      // convert Java Date to SQL Date
      // stmt.setDate(1, new java.sql.Date(playOn.getTime()));
      stmt.setTimestamp(1, new java.sql.Timestamp(playOn.getTime()));

      stmt.executeUpdate();

      LOGGER.info("(DB)--> Match created: playOn=" + playOn);

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
  public int readMaxId() throws MatchException {
    final String sql = "SELECT MAX(id) AS max_id FROM matches";

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

      LOGGER.info("(DB)--> Match ID selected: max_id=" + maxId);

      rs.close();
      // con.commit();
    } catch (SQLException sqe) {
      // try {
      // con.rollback();
      // } catch (SQLException e) {
      // e.printStackTrace();
      // }
      throw new MatchException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }

    return maxId;
  }

  @Override
  public Match read(int id) throws MatchException {
    final String sql = "SELECT * FROM matches WHERE id=?";

    Connection con = null;
    PreparedStatement stmt = null;

    Match match = null;
    try {
      con = DBUtil.getConnection();
      stmt = con.prepareStatement(sql);      
      stmt.setInt(1, id);

      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        rs.next();
        match = new Match(rs.getInt("id"));
        // SQL date to Java date
        Date playOn = new Date(rs.getTimestamp("play_on").getTime());
        match.setPlayOn(playOn);
      }

      LOGGER.info("(DB)--> Match selected: " + match);

      rs.close();
      // con.commit();
    } catch (SQLException sqe) {
      // try {
      // con.rollback();
      // } catch (SQLException e) {
      // e.printStackTrace();
      // }
      throw new MatchException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }

    return match;
  }

  @Override
  public List<Match> readAll() throws MatchException {
    final String sql = "SELECT * FROM matches ORDER BY play_on";

    Connection con = null;
    Statement stmt = null;

    List<Match> matches = new ArrayList<Match>();
    try {
      con = DBUtil.getConnection();
      stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery(sql);
      if (rs != null) {
        while (rs.next()) {
          Match match = new Match(rs.getInt("id"));
          // SQL date to Java date
          Date playOn = new Date(rs.getTimestamp("play_on").getTime());
          match.setPlayOn(playOn);

          matches.add(match);
        }
      }

      LOGGER.info("(DB)--> Matches selected: " + matches);

      rs.close();
      // con.commit();
    } catch (SQLException sqe) {
      // try {
      // con.rollback();
      // } catch (SQLException e) {
      // e.printStackTrace();
      // }
      throw new MatchException(sqe.getMessage());
    } finally {
      DBUtil.close(stmt, con);
    }

    return matches;
  }
}