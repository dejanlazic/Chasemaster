package com.chasemaster.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.chasemaster.Colour;
import com.chasemaster.exception.MatchException;
import com.chasemaster.exception.MovementException;
import com.chasemaster.exception.NoResultException;
import com.chasemaster.exception.NoUniqueResultException;
import com.chasemaster.exception.RegistrationException;
import com.chasemaster.exception.TurnException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.PlayerDAO;
import com.chasemaster.persistence.model.Match;
import com.chasemaster.persistence.model.Player;
import com.chasemaster.persistence.model.Turn;

public class PlayerService {
  private static final Logger LOGGER = Logger.getLogger(PlayerService.class);

  // all used DAO objects
  private PlayerDAO playerDao;

  public PlayerService() throws ServiceException {
    try {
      playerDao = PlayerDAO.getInstance();
    } catch (DAOException e) {
      LOGGER.error(e);
      throw new ServiceException("The following error happened during Player DAO initialization: " + e.getMessage()
          + ". Please contact the application administrator.");
    }
  }

  /**
   * User registration. Only BLACK players can register, WHITE ones get registered automatically from previous match.
   * 
   * @param username
   * @param password
   * @return
   * @throws RegistrationException
   * @throws ServiceException
   */
  public String register(String username, String password) throws RegistrationException, ServiceException {
    return save(username, password, Colour.BLACK);
  }

  public String save(String username, String password, Colour colour) throws RegistrationException, ServiceException {
    LOGGER.debug("Player: " + username);

    // TODO: Validate on client side (JavaScript)
    if (username == null || password == null) {
      throw new RegistrationException("User cannot be null or empty");
    }

    if (playerDao == null) {
      throw new ServiceException("DAO is null");
    }

    String message = null;

    try {
      // try to retrieve a player for a given username
      Player player = playerDao.read(username);
      if (player != null) {
        message = "Player " + username + " already exists";
      }
      // create a user only in case it does not already exists
    } catch (NoResultException e) {
      try {
        // all registered users belong to a group (i.e. black players)
        playerDao.create(username, password, colour.toString());
      } catch (DAOException e1) {
        throw new ServiceException(e1.getMessage());
      }
      message = "Player successfully saved";
    } catch (NoUniqueResultException e) {
      throw new RegistrationException(e.getMessage() + ". Please contact system administrator.");
    } catch (DAOException e) {
      throw new ServiceException(e.getMessage());
    }

    return message;
  }

  /**
   * Retrieves a player for given identifier
   * 
   * @param id
   *          Player identifier
   * @throws ServiceException
   */
  public Player find(int id) throws ServiceException {
    if (playerDao == null) {
      throw new ServiceException("Player DAO is null");
    }

    Player player = null;
    try {
      // try to retrieve a player for a given username
      player = playerDao.read(id);
    } catch (DAOException e) {
      throw new ServiceException(e.getMessage());
    }

    return player;
  }
}