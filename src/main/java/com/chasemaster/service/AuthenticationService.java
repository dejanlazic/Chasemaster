package com.chasemaster.service;

import org.apache.log4j.Logger;

import com.chasemaster.exception.LoginException;
import com.chasemaster.exception.NoResultException;
import com.chasemaster.exception.NoUniqueResultException;
import com.chasemaster.exception.PlayerException;
import com.chasemaster.persistence.AuthenticationDAO;
import com.chasemaster.persistence.DAOException;
import com.chasemaster.persistence.PlayerDAO;
import com.chasemaster.persistence.model.Player;

public class AuthenticationService {
  private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class);

  private AuthenticationDAO authenticationDao;

  public AuthenticationService() throws ServiceException {
    try {
      authenticationDao = AuthenticationDAO.getInstance();
    } catch (DAOException e) {
      LOGGER.error(e);
      throw new ServiceException("The following error happened during Authentication DAO initialization: "
          + e.getMessage() + ". Please contact the application administrator.");
    }
  }

  public Player login(String username, String password) throws ServiceException, LoginException {
    System.out.println("In ChasemasterService#login: " + username);

    // TODO: Check in JavaScript
    if (username == null) {
      throw new RuntimeException("User credentials cannot be null or empty");
    }

    if (authenticationDao == null) {
      throw new RuntimeException("DAO is null");
    }

    Player player = null;
    try {
      player = authenticationDao.authenticate(username, password);
    } catch (DAOException e) {
      throw new ServiceException(e.getMessage());
    } catch (NoResultException e) {
      throw new LoginException("Authentication fails. Incorrect username or password.");
    } catch (NoUniqueResultException e) {
      throw new LoginException(e.getMessage() + ". Please contact administrator.");
    }

    // EmailSender sender = new EmailSender();
    // sender.send(u.getEmail(), "Email text");

    return player;
  }
}