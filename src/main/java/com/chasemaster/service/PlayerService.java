package com.chasemaster.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.chasemaster.exception.NoResultException;
import com.chasemaster.exception.NoUniqueResultException;
import com.chasemaster.exception.PlayerException;
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

  public String register(String username, String password, String passwordConfirmation) throws RegistrationException,
      ServiceException {
    LOGGER.debug("In PlayerService#register: " + username);

    // TODO: Validate on client side (JavaScript)
    if (username == null || password == null) {
      throw new RegistrationException("User cannot be null or empty");
    }

    // TODO: Validate on client side (JavaScript)
    if (!password.equalsIgnoreCase(passwordConfirmation)) {
      throw new RegistrationException("Passwords are not the same");
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
    } catch (NoResultException e) {
      try {
        playerDao.create(username, password);
      } catch (PlayerException e1) {
        message = "?";
        e1.printStackTrace();
      }
      // System.out.println("Created new: " + u);
      message = "Player successfully registered";
    } catch (NoUniqueResultException e) {
      message = e.getMessage() + ". Please contact administrator.";
    } catch (PlayerException e1) {
      message = "?";
      e1.printStackTrace();
    }

    return message;
  }
}