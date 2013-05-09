package com.chasemaster.exception;

import com.chasemaster.Colour;

@SuppressWarnings("serial")
public class NoWinnerException extends MatchException {
  Colour colour;

  public NoWinnerException(Colour colour) {
    super();
    this.colour = colour;
  }

  public Colour getColour() {
    return colour;
  }
}