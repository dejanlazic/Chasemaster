package com.chasemaster.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.chasemaster.exception.MatchException;
import com.chasemaster.exception.TurnException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.MatchDAO;
import com.chasemaster.persistence.MovementDAO;
import com.chasemaster.persistence.TurnDAO;
import com.chasemaster.persistence.model.Match;

public class GameService {
  private static final Logger LOGGER = Logger.getLogger(GameService.class);

  // all used DAO objects
  private MatchDAO matchDao;
  private TurnDAO turnDao;
  private MovementDAO movementDao;

  /**
   * Constructor initiates all required DAO objects
   * 
   * @throws ServiceException
   */
  public GameService() throws ServiceException {
    try {
      matchDao = MatchDAO.getInstance();
      turnDao = TurnDAO.getInstance();
      movementDao = MovementDAO.getInstance();
    } catch (DAOException e) {
      LOGGER.error(e);
      throw new ServiceException("The following error happened during DAO initialization: " + e.getMessage()
          + ". Please contact the application administrator.");
    }
  }

  /**
   * Creates new match
   * 
   * @param playOn Date of match to be played
   * @throws ServiceException
   */
  public void saveMatch(Date playOn) throws ServiceException {
    if (matchDao == null) {
      throw new ServiceException("Match DAO is null");
    }

    try {
      matchDao.create(playOn);      
    } catch (MatchException e) {
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * 
   * @param id
   * @return
   * @throws ServiceException
   */
  public Match getMatch(int id) throws ServiceException {
    // TODO: Implement
    return null;
  }

  /**
   * Retrieves all matches, sorted by date
   * 
   * @return List of matches
   * @throws ServiceException
   */
  public List<Match> getMatches() throws ServiceException {
    if (matchDao == null) {
      throw new ServiceException("Match DAO is null");
    }

    List<Match> matches = null;    
    try {
      matches = matchDao.readAll();      
    } catch (MatchException e) {
      LOGGER.error(e.getMessage());
    }
    
    return matches;
  }

  /**
   * Retrieves the largest Match identifier
   * 
   * @return Maximum Match ID
   * @throws ServiceException
   */
  public int getMaxMatchId() throws ServiceException {
    if (matchDao == null) {
      throw new ServiceException("Match DAO is null");
    }
    
    int maxMatchId = 0;
    try {
      maxMatchId = matchDao.readMaxId(); // retrieve current match id
    } catch (MatchException e) {
      LOGGER.error(e.getMessage());
    }
    
    return maxMatchId;
  }

  /**
   * Creates new record for each BLACK - WHITE turn
   * 
   * @param matchId
   * @param colour
   * @param winners
   * @return Identifier of created turn
   * @throws ServiceException
   */
  public int saveTurn(int matchId, String colour, List<String> winners) throws ServiceException {
    if (turnDao == null) {
      throw new ServiceException("Turn DAO is null");
    }
    
    // concatenates winners' IDs with | character
    int winnersSize = winners.size(); // used to check for | char
    StringBuilder concatWinners = new StringBuilder();
    for(int i = 0; i < winnersSize; i++) {
      concatWinners.append(winners.get(i));
      if(i < winnersSize -1) {
        concatWinners.append("|");
      }
    }

    int maxMatchId = 0;
    try {
      turnDao.create(matchId, colour, concatWinners.toString());
      maxMatchId = turnDao.readMaxId(); // retrieve current match id
    } catch (TurnException e) {
      LOGGER.error(e.getMessage());
    }
    
    return maxMatchId;
  }

  /**
   * Creates new movement being performed
   * 
   * @param turnId
   * @param playerId
   * @param locationFrom
   * @param locationTo
   * @param duration Duration of movement performed
   * @throws ServiceException
   */
  public void saveMovement(int turnId, int playerId, String locationFrom, String locationTo, long duration) throws ServiceException {
    if (movementDao == null) {
      throw new ServiceException("Movement DAO is null");
    }
    
    try {
      movementDao.create(turnId, playerId, locationFrom, locationTo, duration);
    } catch (MatchException e) {
      LOGGER.error(e.getMessage());
    }
  }
}