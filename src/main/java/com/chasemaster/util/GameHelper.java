package com.chasemaster.util;

import javax.servlet.ServletContext;
import static com.chasemaster.util.GameConst.*;

import com.chasemaster.exception.NoObjectInContextException;

public class GameHelper {
  private ServletContext context;
    
  public GameHelper() {
    super();
  }

  public GameHelper(ServletContext context) {
    super();
    this.context = context;
  }

  public void changeTurn() {
    Boolean turn = (Boolean) context.getAttribute(TURN_WHITE);
    if(turn == null) {
      throw new NoObjectInContextException(TURN_WHITE);
    }
    context.setAttribute(TURN_WHITE, !turn);
  }
  
  public Boolean isTurnWhite() {
    Boolean turn = (Boolean) context.getAttribute(TURN_WHITE);
    if(turn == null) {
      throw new NoObjectInContextException(TURN_WHITE);
    }
    return turn;
  }
}