package com.mgs.chess.core;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mgs.chess.core.ChessAnaliserImpl;
import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.ChessReader;
import com.mgs.chess.core.ChessReaderImpl;
import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.Piece;
import com.mgs.chess.core.movement.Movement;

public class ChessAnaliserImplIntegrationTest {
  private ChessAnaliserImpl testObj;
  private ChessReader chessReader = new ChessReaderImpl();
  private ChessBoard board;

  @BeforeMethod(groups = "integration")
  public void setup() {
    testObj = new ChessAnaliserImpl(chessReader);
  }

  @Test(groups = "integration")
  public void testIsTheatening() {
    // GIVEN
    board = new ChessBoard().addPiece(Piece.WHITE_ROOK, Location.A1).addPiece(Piece.WHITE_KNIGHT, Location.A6)
        .addPiece(Piece.BLACK_KNIGHT, Location.C1);

    Assert.assertTrue(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A2));
    Assert.assertTrue(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A3));
    Assert.assertTrue(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A4));
    Assert.assertTrue(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A5));
    Assert.assertFalse(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.C4));
    Assert.assertFalse(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A6));
    Assert.assertFalse(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A7));
    Assert.assertTrue(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.B1));
    Assert.assertTrue(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.C1));
    Assert.assertFalse(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.D1));
    Assert.assertFalse(testObj.isThreathening(board, board.getPieceOnLocation(Location.A1), Location.A8));
  }

  @Test(groups = "integration")
  public void testIsInCheck() {
    // GIVEN
    board = new ChessBoard().addPiece(Piece.WHITE_KING, Location.D1).addPiece(Piece.BLACK_QUEEN, Location.A4)
        .addPiece(Piece.WHITE_KNIGHT, Location.C1);

    Assert.assertTrue(testObj.isInCheck(board, Color.WHITE));
  }

  @Test(groups = "integration")
  public void testFindReachableLocations_forRook_withNoObstacles() {
    // GIVEN
    board = new ChessBoard().addPiece(Piece.WHITE_ROOK, Location.A1).addPiece(Piece.WHITE_KING, Location.C8);

    // WHEN
    List<Location> actualReachablePositions = testObj.findReachableLocations(board.getPieceOnLocation(Location.A1),
        board, null);

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 14);
    Assert.assertEquals(actualReachablePositions.get(0), Location.B1);
    Assert.assertEquals(actualReachablePositions.get(1), Location.C1);
    Assert.assertEquals(actualReachablePositions.get(2), Location.D1);
    Assert.assertEquals(actualReachablePositions.get(3), Location.E1);
    Assert.assertEquals(actualReachablePositions.get(4), Location.F1);
    Assert.assertEquals(actualReachablePositions.get(5), Location.G1);
    Assert.assertEquals(actualReachablePositions.get(6), Location.H1);
    Assert.assertEquals(actualReachablePositions.get(7), Location.A2);
    Assert.assertEquals(actualReachablePositions.get(8), Location.A3);
    Assert.assertEquals(actualReachablePositions.get(9), Location.A4);
    Assert.assertEquals(actualReachablePositions.get(10), Location.A5);
    Assert.assertEquals(actualReachablePositions.get(11), Location.A6);
    Assert.assertEquals(actualReachablePositions.get(12), Location.A7);
    Assert.assertEquals(actualReachablePositions.get(13), Location.A8);
  }

  @Test(groups = "integration")
  public void testFindReachableLocations_forRook_withSameColorObstacles() {
    // GIVEN
    board = new ChessBoard().addPiece(Piece.WHITE_ROOK, Location.A1).addPiece(Piece.WHITE_QUEEN, Location.A5)
        .addPiece(Piece.WHITE_KNIGHT, Location.C1).addPiece(Piece.WHITE_KING, Location.C8);

    // WHEN
    List<Location> actualReachablePositions = testObj.findReachableLocations(board.getPieceOnLocation(Location.A1),
        board, null);

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 4);
    Assert.assertEquals(actualReachablePositions.get(0), Location.B1);
    Assert.assertEquals(actualReachablePositions.get(1), Location.A2);
    Assert.assertEquals(actualReachablePositions.get(2), Location.A3);
    Assert.assertEquals(actualReachablePositions.get(3), Location.A4);
  }

  @Test(groups = "integration")
  public void testFindReachableLocations_forRook_withDifferentColorObstacles() {
    // GIVEN
    board = new ChessBoard().addPiece(Piece.WHITE_ROOK, Location.A1).addPiece(Piece.BLACK_QUEEN, Location.A5)
        .addPiece(Piece.WHITE_KNIGHT, Location.C1).addPiece(Piece.WHITE_KING, Location.C8);

    // WHEN
    List<Location> actualReachablePositions = testObj.findReachableLocations(board.getPieceOnLocation(Location.A1),
        board, null);

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 5);
    Assert.assertEquals(actualReachablePositions.get(0), Location.B1);
    Assert.assertEquals(actualReachablePositions.get(1), Location.A2);
    Assert.assertEquals(actualReachablePositions.get(2), Location.A3);
    Assert.assertEquals(actualReachablePositions.get(3), Location.A4);
    Assert.assertEquals(actualReachablePositions.get(4), Location.A5);
  }

  @Test(groups = "integration")
  public void testFindReachableLocations_forRook_whenCantMove() {
    // GIVEN
    board = new ChessBoard().addPiece(Piece.WHITE_KING, Location.D1).addPiece(Piece.WHITE_ROOK, Location.C2)
        .addPiece(Piece.BLACK_QUEEN, Location.A4).addPiece(Piece.WHITE_KNIGHT, Location.C1);

    // WHEN
    List<Location> actualReachablePositions = testObj.findReachableLocations(board.getPieceOnLocation(Location.C2),
        board, null);

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 0);
  }

  @Test(groups = "integration")
  public void testFindReachableLocations_forPawn_whenInFirstRow() {
    // GIVEN
    board = new ChessBoard().addPiece(Piece.BLACK_PAWN, Location.A7).addPiece(Piece.BLACK_KING, Location.D4);

    // WHEN
    List<Location> actualReachablePositions = testObj.findReachableLocations(board.getPieceOnLocation(Location.A7),
        board, null);

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 2);
    Assert.assertEquals(actualReachablePositions.get(0), Location.A6);
    Assert.assertEquals(actualReachablePositions.get(1), Location.A5);
  }

  @Test(groups = "integration")
  public void testFindReachableLocations_forPawn_enPassant() {
    // GIVEN
    board = new ChessBoard().addPiece(Piece.BLACK_PAWN, Location.C5).addPiece(Piece.WHITE_PAWN, Location.D5)
        .addPiece(Piece.WHITE_KING, Location.D4);

    Movement enPassantEnabler = new Movement(Piece.BLACK_PAWN, Location.C7, Location.C5);
    // WHEN
    List<Location> actualReachablePositions = testObj.findReachableLocations(board.getPieceOnLocation(Location.D5),
        board, enPassantEnabler);

    // THEN
    Assert.assertEquals(actualReachablePositions.size(), 1);
    Assert.assertEquals(actualReachablePositions.get(0), Location.C6);
  }
}
