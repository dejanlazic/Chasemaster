package com.mgs.chess.core;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.Piece;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.SquareContent;
import com.mgs.chess.core.movement.Movement;

public class ChessBoardFinalTest {
  private ChessReader chessReader = new ChessReaderImpl();
  private ChessAnaliserImpl chessAnaliser;
	private ChessBoard board;
	
	@BeforeMethod(groups="unit")
	public void setup () {
	  chessAnaliser = new ChessAnaliserImpl(chessReader);
	  
		board = new ChessBoard().
			addPiece(Piece.WHITE_ROOK, 	Location.A1).
			addPiece(Piece.WHITE_KNIGHT, Location.B1).
			addPiece(Piece.WHITE_BISHOP, Location.C1).
			addPiece(Piece.WHITE_KING, Location.D1).
			addPiece(Piece.WHITE_QUEEN, Location.E1).
			addPiece(Piece.WHITE_BISHOP, Location.F1).
			addPiece(Piece.WHITE_KNIGHT, Location.G1).			
			addPiece(Piece.WHITE_ROOK, Location.H1).
			
			addPiece(Piece.WHITE_PAWN, Location.A2).
			addPiece(Piece.WHITE_PAWN, Location.B2).
			addPiece(Piece.WHITE_PAWN, Location.C2).
			addPiece(Piece.WHITE_PAWN, Location.D2).
			addPiece(Piece.WHITE_PAWN, Location.E2).
			addPiece(Piece.WHITE_PAWN, Location.F2).
			addPiece(Piece.WHITE_PAWN, Location.G2).
			addPiece(Piece.WHITE_PAWN, Location.H2).
			
      addPiece(Piece.BLACK_PAWN, Location.A7).
      addPiece(Piece.BLACK_PAWN, Location.B7).
      addPiece(Piece.BLACK_PAWN, Location.C7).
      addPiece(Piece.BLACK_PAWN, Location.D7).
      addPiece(Piece.BLACK_PAWN, Location.E7).
      addPiece(Piece.BLACK_PAWN, Location.F7).
      addPiece(Piece.BLACK_PAWN, Location.G7).
      addPiece(Piece.BLACK_PAWN, Location.H7).
      
      addPiece(Piece.BLACK_ROOK,  Location.A8).
      addPiece(Piece.BLACK_KNIGHT, Location.B8).
      addPiece(Piece.BLACK_BISHOP, Location.C8).
      addPiece(Piece.BLACK_KING, Location.D8).
      addPiece(Piece.BLACK_QUEEN, Location.E8).
      addPiece(Piece.BLACK_BISHOP, Location.F8).
      addPiece(Piece.BLACK_KNIGHT, Location.G8).      
      addPiece(Piece.BLACK_ROOK, Location.H8)      
			;
	}

  /*
   *  1) When a piece is moved, check if the movement is valid 
   *    (i.e. if it is in a list of reachable positions)
   */
  @Test(groups = "unit")
  public void testFindReachableLocations_WithNoObstacles() {
    // BLACK_PAWN
    System.out.println("-> testFindReachableLocations_WithNoObstacles: " + board.getPieceOnLocation(Location.H7));
    List<Location> actualReachablePositions = chessAnaliser.findReachableLocations(
        board.getPieceOnLocation(Location.H7), board, null);
    for (Location position : actualReachablePositions) {
      System.out.println(position.getCoordinateX() + "," + position.getCoordinateY() + " ("
          + Location.forCoordinates(position.getCoordinateX(), position.getCoordinateY()) + ")");
    }

    // BLACK_PAWN
    System.out.println("-> testFindReachableLocations_WithNoObstacles: " + board.getPieceOnLocation(Location.A7));
    actualReachablePositions = chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.A7), board, null);
    for (Location position : actualReachablePositions) {
      System.out.println(position.getCoordinateX() + "," + position.getCoordinateY() + " ("
          + Location.forCoordinates(position.getCoordinateX(), position.getCoordinateY()) + ")");
    }

    // BLACK_ROOK
    board = new ChessBoard().addPiece(Piece.BLACK_KING, Location.D8).addPiece(Piece.BLACK_ROOK,  Location.A8);
    System.out.println("-> testFindReachableLocations_WithNoObstacles: " + board.getPieceOnLocation(Location.D8));
    actualReachablePositions = chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.A8), board, null);
    for (Location position : actualReachablePositions) {
      System.out.println(position.getCoordinateX() + "," + position.getCoordinateY() + " ("
          + Location.forCoordinates(position.getCoordinateX(), position.getCoordinateY()) + ")");
    }

    // BLACK_KNIGHT
    board = new ChessBoard().addPiece(Piece.BLACK_KING, Location.D8).addPiece(Piece.BLACK_KNIGHT,  Location.B8);
    System.out.println("-> testFindReachableLocations_WithNoObstacles: " + board.getPieceOnLocation(Location.B8));
    actualReachablePositions = chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.B8), board, null);
    for (Location position : actualReachablePositions) {
      System.out.println(position.getCoordinateX() + "," + position.getCoordinateY() + " ("
          + Location.forCoordinates(position.getCoordinateX(), position.getCoordinateY()) + ")");
    }
    
    // WHITE_BISHOP
    board = new ChessBoard().addPiece(Piece.WHITE_KING, Location.D1).addPiece(Piece.WHITE_BISHOP, Location.C1);
    System.out.println("-> testFindReachableLocations_WithNoObstacles: " + board.getPieceOnLocation(Location.C1));
    actualReachablePositions = chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.C1), board, null);
    for (Location position : actualReachablePositions) {
      System.out.println(position.getCoordinateX() + "," + position.getCoordinateY() + " ("
          + Location.forCoordinates(position.getCoordinateX(), position.getCoordinateY()) + ")");
    }

    // -----------
    Assert.assertTrue(true);
  }

  /*
   * This case is covered by 1)
   */
  @Test(groups = "unit")
  public void testFindReachableLocations__ForRook_WithSameColorObstacles() {
    // GIVEN
    board = new ChessBoard()
      .addPiece(Piece.WHITE_ROOK, Location.A1)
      .addPiece(Piece.WHITE_QUEEN, Location.A5)
      .addPiece(Piece.WHITE_KNIGHT, Location.C1)
      .addPiece(Piece.WHITE_KING, Location.C8);

    // WHEN
    List<Location> actualReachablePositions = chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.A1), board, null);
    
    System.out.println("-> testFindReachableLocations__ForRook_WithSameColorObstacles: " + board.getPieceOnLocation(Location.A1));
    for (Location position : actualReachablePositions) {
      System.out.println(position.getCoordinateX() + "," + position.getCoordinateY() + " ("
          + Location.forCoordinates(position.getCoordinateX(), position.getCoordinateY()) + ")");
    }

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 4);
    Assert.assertEquals(actualReachablePositions.get(0), Location.B1);
    Assert.assertEquals(actualReachablePositions.get(1), Location.A2);
    Assert.assertEquals(actualReachablePositions.get(2), Location.A3);
    Assert.assertEquals(actualReachablePositions.get(3), Location.A4);
  }

  /*
   * This case is covered by 1)
   */
  @Test(groups = "unit")
  public void testFindReachableLocations_forRook_withDifferentColorObstacles() {
    // GIVEN
    board = new ChessBoard()
      .addPiece(Piece.WHITE_ROOK, Location.A1)
      .addPiece(Piece.BLACK_QUEEN, Location.A5)
      .addPiece(Piece.WHITE_KNIGHT, Location.C1)
      .addPiece(Piece.WHITE_KING, Location.C8);

    // WHEN
    List<Location> actualReachablePositions = chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.A1), board, null);

    System.out.println("-> testFindReachableLocations_forRook_withDifferentColorObstacles: " + board.getPieceOnLocation(Location.A1));
    for (Location position : actualReachablePositions) {
      System.out.println(position.getCoordinateX() + "," + position.getCoordinateY() + " ("
          + Location.forCoordinates(position.getCoordinateX(), position.getCoordinateY()) + ")");
    }

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 5);
    Assert.assertEquals(actualReachablePositions.get(0), Location.B1);
    Assert.assertEquals(actualReachablePositions.get(1), Location.A2);
    Assert.assertEquals(actualReachablePositions.get(2), Location.A3);
    Assert.assertEquals(actualReachablePositions.get(3), Location.A4);
    Assert.assertEquals(actualReachablePositions.get(4), Location.A5);
  }

  /*
   * 1) Check if a piece can move 
   * (i.e. if reachable positions list is >0)
   */
  @Test(groups = "unit")
  public void testFindReachableLocations_ForRook_WhenCannotMove() {
    // GIVEN (WHITE_KING in check if WHITE_ROOK moves)
    board = new ChessBoard()
      .addPiece(Piece.WHITE_ROOK, Location.C2) 
      .addPiece(Piece.WHITE_KING, Location.D1)      
      .addPiece(Piece.BLACK_QUEEN, Location.A4)
      .addPiece(Piece.WHITE_KNIGHT, Location.C1);

    // WHEN
    List<Location> actualReachablePositions = chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.C2), board, null);

    System.out.println("-> testFindReachableLocations_ForRook_WhenCannotMove: " + board.getPieceOnLocation(Location.C2));
    for (Location position : actualReachablePositions) {
      System.out.println(position.getCoordinateX() + "," + position.getCoordinateY() + " ("
          + Location.forCoordinates(position.getCoordinateX(), position.getCoordinateY()) + ")");
    }

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 0);
  }

  
  @Test(groups = "unit")
  public void testFindReachableLocations_ForPawn_WhenInFirstRow() {
    // GIVEN
    board = new ChessBoard().addPiece(Piece.BLACK_PAWN, Location.A7).addPiece(Piece.BLACK_KING, Location.D4);

    // WHEN
    List<Location> actualReachablePositions = chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.A7), board, null);

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 2);
    Assert.assertEquals(actualReachablePositions.get(0), Location.A6);
    Assert.assertEquals(actualReachablePositions.get(1), Location.A5);
  }

  @Test(groups = "unit")
  public void testFindReachableLocations_ForPawn_EnPassant() {
    // GIVEN
    board = new ChessBoard()
      .addPiece(Piece.BLACK_PAWN, Location.C5)
      .addPiece(Piece.WHITE_PAWN, Location.D5)
      .addPiece(Piece.WHITE_KING, Location.D4);

    // previous movement
    Movement enPassantEnabler = new Movement(Piece.BLACK_PAWN, Location.C7, Location.C5);
    
    // WHEN
    List<Location> actualReachablePositions = 
        chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.D5), board, enPassantEnabler);
    
    System.out.println("-> testFindReachableLocations_ForPawn_EnPassant: " + board.getPieceOnLocation(Location.D5));
    for (Location position : actualReachablePositions) {
      System.out.println(position.getCoordinateX() + "," + position.getCoordinateY() + " ("
          + Location.forCoordinates(position.getCoordinateX(), position.getCoordinateY()) + ")");
    }

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 1);
    Assert.assertEquals(actualReachablePositions.get(0), Location.C6);
  }
  
  /*
   * 2) After every movement check if the opposite king is in check 
   */
  @Test(groups = "unit")
  public void testIsInCheck() {
    board = new ChessBoard()
      .addPiece(Piece.WHITE_KING, Location.D1)
      .addPiece(Piece.BLACK_QUEEN, Location.A4)
      .addPiece(Piece.WHITE_KNIGHT, Location.C1);

    Assert.assertTrue(chessAnaliser.isInCheck(board, Color.WHITE));
  }

  /*
   * 3) After every movement check if the opposite king is in check mate 
   */
  @Test(groups = "unit")
  public void testCheckMate() {
    board = new ChessBoard()
      .addPiece(Piece.WHITE_KING, Location.A1) // in mate
      .addPiece(Piece.BLACK_QUEEN, Location.C1)      
      .addPiece(Piece.BLACK_ROOK, Location.A3)
      .addPiece(Piece.BLACK_KING, Location.H8);

    Assert.assertTrue(chessAnaliser.isCheckMate(board));

    board = new ChessBoard()
    .addPiece(Piece.WHITE_KING, Location.H2) // NOT in mate
    .addPiece(Piece.BLACK_QUEEN, Location.C1)      
    .addPiece(Piece.BLACK_ROOK, Location.A3)
    .addPiece(Piece.BLACK_KING, Location.H8);

    Assert.assertFalse(chessAnaliser.isCheckMate(board));
  }


  /*
   * ?
   */
  @Test(groups = "unit")
  public void testIsTheatening() {
    board = new ChessBoard()
      .addPiece(Piece.WHITE_ROOK, Location.A1)
      .addPiece(Piece.WHITE_KNIGHT, Location.A6)
      .addPiece(Piece.BLACK_KNIGHT, Location.C1);

    Assert.assertTrue(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A2));
    Assert.assertTrue(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A3));
    Assert.assertTrue(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A4));
    Assert.assertTrue(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A5));
    Assert.assertFalse(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.C4));
    Assert.assertFalse(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A6));
    Assert.assertFalse(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A7));
    Assert.assertTrue(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.B1));
    Assert.assertTrue(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.C1));
    Assert.assertFalse(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.D1));
    Assert.assertFalse(chessAnaliser.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A8));
  }

  
  /*
   * 3) Check if TO field is empty 
   */
  @Test(groups = "unit")
  public void testCanRemovePiece() {
    board = new ChessBoard()
      .addPiece(Piece.BLACK_QUEEN, Location.A1) // attacker
      .addPiece(Piece.WHITE_QUEEN, Location.C1) // blocker
      .addPiece(Piece.WHITE_KING, Location.D1) // attacked
      .addPiece(Piece.BLACK_ROOK, Location.C2) // protector
      .addPiece(Piece.BLACK_KING, Location.A8); // some position

    System.out.println("-> testCanRemovePiece");
    
    // 1) if C1 is reachable, i.e. if targeted field is a valid movement 
    List<Location> actualReachablePositions = chessAnaliser.findReachableLocations(board.getPieceOnLocation(Location.A1), board, null);
    System.out.println("Reachable positions: " + actualReachablePositions.size());
    for (Location reachablePosition : actualReachablePositions) {
//      System.out.println(reachablePosition.getCoordinateX() + "," + reachablePosition.getCoordinateY() + " ("
//          + Location.forCoordinates(reachablePosition.getCoordinateX(), reachablePosition.getCoordinateY()) + ")");
      
//      if(reachablePosition.getCoordinateX() == Location.C1.getCoordinateX() 
//          && reachablePosition.getCoordinateY() == Location.C1.getCoordinateY()) {
      if(reachablePosition.equals(Location.C1)) {
        System.out.println("Targeted position: " + reachablePosition.getCoordinateX() + "," + reachablePosition.getCoordinateY() + " ("
            + Location.forCoordinates(reachablePosition.getCoordinateX(), reachablePosition.getCoordinateY()) + ")");
        
        // 2) check if targeted field is empty
        PieceOnLocation pieceOnTargetedField = board.getPieceOnLocation(reachablePosition);
        System.out.println("Is targeted position empty? " + (pieceOnTargetedField==null));
        if (pieceOnTargetedField != null) {
          // remove a piece
          board = board.empty(reachablePosition);
          
          // test if removed
          try {
            pieceOnTargetedField = board.getPieceOnLocation(reachablePosition);
          } catch(com.mgs.chess.core.PieceNotFoundException e) {
            pieceOnTargetedField = null;
            System.out.println("Targeted position is now empty. ");
          }
        }
        
        // move ATTACKER to targeted field
        board = board.performMovement(Piece.BLACK_QUEEN, Location.A1, Location.C1);

        Assert.assertEquals(board.getSquareContent(Location.A1), SquareContent.EMPTY_SQUARE);
        Assert.assertEquals(board.getSquareContent(Location.C1), Piece.BLACK_QUEEN);
        Assert.assertEquals(board.getSquareContent(Location.C2), Piece.BLACK_ROOK);
        
        // is King in check
        Assert.assertTrue(chessAnaliser.isInCheck(board, Color.WHITE));
        Assert.assertTrue(chessAnaliser.isCheckMate(board));
      }
    }
  }

  
  // ----------
  
  
  // TODO: Check 
  @Test(groups = "unit")
  public void testPerformMovement() {
    board = new ChessBoard().
        addPiece(Piece.WHITE_ROOK,  Location.A1).
        addPiece(Piece.WHITE_KING,  Location.D1).
        addPiece(Piece.WHITE_QUEEN, Location.G7).
        addPiece(Piece.BLACK_KING,  Location.H4);
    
    ChessBoard newBoard = board.performMovement(Piece.WHITE_ROOK, Location.A1, Location.A8);

    Assert.assertEquals(board.getSquareContent(Location.A1), Piece.WHITE_ROOK);
    Assert.assertEquals(board.getSquareContent(Location.A8), SquareContent.EMPTY_SQUARE);

    Assert.assertEquals(newBoard.getSquareContent(Location.A1), SquareContent.EMPTY_SQUARE);
    Assert.assertEquals(newBoard.getSquareContent(Location.A8), Piece.WHITE_ROOK);
  }

  @Test
  public void testGetPieces() {
    board = new ChessBoard().
        addPiece(Piece.WHITE_ROOK,  Location.A1).
        addPiece(Piece.WHITE_KING,  Location.D1).
        addPiece(Piece.WHITE_QUEEN, Location.G7).
        addPiece(Piece.BLACK_KING,  Location.H4);
    
    List<PieceOnLocation> whitePieces = board.getPieces(Color.WHITE);
    List<PieceOnLocation> blackPieces = board.getPieces(Color.BLACK);

    Assert.assertEquals(whitePieces.size(), 3);
    Assert.assertEquals(whitePieces.get(0), Piece.WHITE_ROOK.on(Location.A1));
    Assert.assertEquals(whitePieces.get(1), Piece.WHITE_KING.on(Location.D1));
    Assert.assertEquals(whitePieces.get(2), Piece.WHITE_QUEEN.on(Location.G7));

    Assert.assertEquals(blackPieces.size(), 1);
    Assert.assertEquals(blackPieces.get(0), Piece.BLACK_KING.on(Location.H4));
  }

  @Test
  public void testFindPiece() {
    board = new ChessBoard().
        addPiece(Piece.WHITE_ROOK,  Location.A1).
        addPiece(Piece.WHITE_KING,  Location.D1).
        addPiece(Piece.WHITE_QUEEN, Location.G7).
        addPiece(Piece.BLACK_KING,  Location.H4);
    
    List<PieceOnLocation> whiteRooks = board.find(Piece.WHITE_ROOK);

    Assert.assertEquals(whiteRooks.size(), 1);
    Assert.assertEquals(whiteRooks.get(0).getType(), PieceType.ROOK);
    Assert.assertEquals(whiteRooks.get(0).getLocation(), Location.A1);
  }
}