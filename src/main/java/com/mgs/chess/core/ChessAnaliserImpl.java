package com.mgs.chess.core;

import java.util.ArrayList;
import java.util.List;

import com.mgs.chess.core.movement.Movement;
import com.mgs.chess.core.movement.MovementLine;
import com.mgs.chess.core.movement.MovementLines;
import com.mgs.chess.core.movement.type.ConditionalMovementType;
import com.mgs.chess.core.movement.type.MovementType;

public class ChessAnaliserImpl implements ChessAnaliser {
  final ChessReader chessReader;

  public ChessAnaliserImpl(ChessReader chessReader) {
    this.chessReader = chessReader;
  }

  @Override
  public boolean isInCheck(ChessBoard board, Color color) {
    boolean isInCheck = false;
    List<PieceOnLocation> aux = board.find(PieceType.KING.withColor(color));
    if (aux.size() != 1)
      throw new IllegalBoardException();
    PieceOnLocation king = aux.get(0);

    for (MovementLine movementLine : chessReader.findAllMovementLines(king.getLocation())) {
      for (Location location : movementLine.getLocations()) {
        SquareContent squareContent = board.getSquareContent(location);
        if (!squareContent.isEmpty() && squareContent.asPiece().colorIs(color.opposite())) {
          isInCheck = isThreathening(board, board.getPieceOnLocation(location), king.getLocation());
          if (isInCheck) {
            return isInCheck;
          } else {
            break;
          }
        }
      }
    }
    return isInCheck;
  }

  public boolean isThreathening(ChessBoard board, PieceOnLocation pieceOnLocation, Location locationTo) {
    Color color = pieceOnLocation.getColor();

    MovementLines movementLines = chessReader.findMovementLines(pieceOnLocation);
    for (MovementLine movementLine : movementLines) {
      List<Location> reachableLocations = movementLine.filterPotentiallyReachablePositions(color, board);
      if (reachableLocations.contains(locationTo))
        return true;
    }
    return false;
  }

  @Override
  public Movement findCheckMateMovement(ChessBoard board, Movement previousMovement, Color color) {
    List<PieceOnLocation> allPieces = board.getPieces(color);

    for (PieceOnLocation pieceInBoard : allPieces) {
      List<Location> possibleLocations = findReachableLocations(pieceInBoard, board, previousMovement);
      for (Location locationTo : possibleLocations) {
        Movement movement = new Movement(pieceInBoard, locationTo);
        ChessBoard newBoard = board.performMovement(movement);
        if (isCheckMate(newBoard)) {
          return movement;
        }
      }
    }
    return null;
  }

  @Override
  public List<Location> findReachableLocations(PieceOnLocation pieceOnLocation, ChessBoard board,
      Movement previousMovement) {
    Piece piece = pieceOnLocation.getPiece();
    List<Location> toReturn = new ArrayList<Location>();

    for (MovementLine movementLine : chessReader.findMovementLines(pieceOnLocation)) {
      if (movementLine.isApplicable(board, previousMovement)) {
        List<Location> potentiallyReachablePositions = movementLine.filterPotentiallyReachablePositions(
            pieceOnLocation.getColor(), board);
        for (Location location : potentiallyReachablePositions) {
          ChessBoard nextPossibleBoard = nextBoard(board, movementLine.getType(), pieceOnLocation, location,
              previousMovement);
          if (!isInCheck(nextPossibleBoard, piece.getColor())) {
            toReturn.add(location);
          }
        }
      }
    }

    return toReturn;
  }

  ChessBoard nextBoard(ChessBoard board, MovementType movementType, PieceOnLocation pieceOnLocation, Location location,
      Movement previousMovement) {
    ChessBoard newBoard = board.performMovement(pieceOnLocation, location);
    if ((movementType != ConditionalMovementType.DIAGONAL_LEFT_FORWARD_ON_EN_PASSANT)
        && (movementType != ConditionalMovementType.DIAGONAL_RIGHT_FORWARD_ON_EN_PASSANT)) {
      return newBoard;
    } else {
      return newBoard.empty(previousMovement.getTo());
    }
  }

  public boolean isCheckMate(ChessBoard chessBoard) {
    System.out.println("In isCheckMate: ");

    PieceOnLocation blackKing = chessBoard.find(Piece.BLACK_KING).get(0);
    PieceOnLocation whiteKing = chessBoard.find(Piece.WHITE_KING).get(0);
    
    System.out.println(blackKing + ", " + whiteKing);
    
    List<Location> blackKingReachablePositions = findReachableLocations(chessBoard.getPieceOnLocation(blackKing.getLocation()), chessBoard, null);
    List<Location> whiteKingReachablePositions = findReachableLocations(chessBoard.getPieceOnLocation(whiteKing.getLocation()), chessBoard, null);
    
    System.out.println(blackKingReachablePositions.size() + ", " + whiteKingReachablePositions.size());
    
    // if either WHITE or BLACK king cannot move, it is check mate 
    return blackKingReachablePositions.size()==0 || whiteKingReachablePositions.size()==0;
  }
}
