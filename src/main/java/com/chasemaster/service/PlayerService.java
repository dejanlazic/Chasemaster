package com.chasemaster.service;

import org.apache.log4j.Logger;

import com.chasemaster.Colour;
import com.chasemaster.exception.NoResultException;
import com.chasemaster.exception.NoUniqueResultException;
import com.chasemaster.exception.RegistrationException;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.PlayerDAO;
import com.chasemaster.persistence.model.Player;

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

  public String register(String username, String password) throws RegistrationException, ServiceException {
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
      Player player = playerDao.find(username);
      if (player != null) {
        message = "Player " + username + " already exists";
      }
      // create a user only in case it does not already exists
    } catch (NoResultException e) {
      try {
        // all registered users belong to a group (i.e. black players)
        playerDao.create(username, password, Colour.BLACK.toString());
      } catch (DAOException e1) {
        throw new ServiceException(e1.getMessage());
      }
      message = "Player successfully registered";
    } catch (NoUniqueResultException e) {
      throw new RegistrationException(e.getMessage() + ". Please contact system administrator.");
    } catch (DAOException e) {
      throw new ServiceException(e.getMessage());
    }

    return message;
  }
}