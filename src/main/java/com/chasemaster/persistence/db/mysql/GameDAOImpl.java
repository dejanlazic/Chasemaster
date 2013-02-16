package com.chasemaster.persistence.db.mysql;

import org.apache.log4j.Logger;

import com.chasemaster.exception.GameException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.GameDAO;
import com.chasemaster.persistence.db.DBConfig;

/**
 * An implementation of the UserDAO that uses MySQL JDBC.
 */
public class GameDAOImpl extends GameDAO {
	private final Logger LOGGER = Logger.getLogger(GameDAOImpl.class);

	/**
	 * Construct the data adapter and load the JDBC driver.
	 */
	public GameDAOImpl() throws DAOException {
		try {
			Class.forName(DBConfig.getJDBCDriverClassName());			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DAOException("Unable to load JDBC driver: "
					+ DBConfig.getJDBCDriverClassName());
		}
	}

  @Override
  public void performMovement() throws GameException {
    // TODO Auto-generated method stub    
  }
}