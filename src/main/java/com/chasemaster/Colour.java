package com.chasemaster;

public enum Colour {
  WHITE, BLACK;
  
  public static Colour forString (String colour) {
    return Colour.valueOf(colour);
  }
  
  @Override
  public String toString() {
    return super.toString();
  }

  public static void main(String[] args) {
    System.out.println(Colour.WHITE + ", " + Colour.WHITE.toString());
    System.out.println(Colour.forString("BLACK"));
    //System.out.println(Colour.forString("black"));
  }
}