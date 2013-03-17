package com.chasemaster.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import static com.chasemaster.util.GameConst.*;

import com.chasemaster.exception.NoObjectInContextException;
import com.mgs.chess.core.ChessAnaliserImpl;
import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.ChessReader;
import com.mgs.chess.core.ChessReaderImpl;
import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.Piece;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.movement.Movement;

public class GameHelper {
  private static final Logger LOGGER = Logger.getLogger(GameHelper.class);
  
  private ServletContext context;
  private ChessReader chessReader = new ChessReaderImpl();
  private ChessAnaliserImpl chessAnaliser;
  
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
  
  public ChessBoard prepareBoard () {
    chessAnaliser = new ChessAnaliserImpl(chessReader);
    
    ChessBoard board = new ChessBoard().
      addPiece(Piece.WHITE_ROOK,  Location.A1).
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
      addPiece(Piece.BLACK_ROOK, Location.H8);
    
    return board;
  }

  public ChessBoard getBoard() {
    return (ChessBoard)context.getAttribute(CHESSBOARD);
  }

  public Piece getPiece(Location location) {
    return getBoard().getPieceOnLocation(location).getPiece();
  }

  /*
   * Takes all pieces on the board and populates a map with their images
   */
  public Map<String, String> getBoardImages() {
    Map<String, String> boardImages = new HashMap<String, String>();
    
    ChessBoard board = getBoard();
    if(board != null) {
      List<PieceOnLocation> pieces = board.getPieces(Color.WHITE);
      for(PieceOnLocation piece : pieces) {
        LOGGER.debug("Piece on board: " + piece.getLocation().toString() + ", " + piece.getPiece() + ", " + piece.getPiece().getImageName());
        boardImages.put(piece.getLocation().toString(), piece.getPiece().getImageName());
      }
      
      pieces = board.getPieces(Color.BLACK);
      for(PieceOnLocation piece : pieces) {
        LOGGER.debug("Piece on board: \'" + piece.getLocation().toString() + "\', " + piece.getPiece() + ", " + piece.getPiece().getImageName());
        boardImages.put(piece.getLocation().toString(), piece.getPiece().getImageName());
      }
    }
    
    return boardImages;
  }
  
  // TODO: Implement
  public Movement determineWinningMovement(Map<String, Movement> playerMovementPairs) {
    Movement movement = null;

    /*
     * Contains both user id and movement, only for this purpose
     */
    class UserMovement {
      private final String userId;
      private final Movement movement;
      
      public UserMovement(String userId, Movement movement) {
        this.userId = userId;
        this.movement = movement;
      }

      public String getUserId() {
        return userId;
      }
      public Movement getMovement() {
        return movement;
      }         
    }

    // contains list of all movements, grouped by TO field
    Map<String, UserMovement> movementsByLocation = new HashMap<String, UserMovement>();
    
    for(Map.Entry<String, Movement> entry : playerMovementPairs.entrySet()) {
      LOGGER.debug("From playerMovementPairs: " + entry.getKey() + ", " + entry.getValue());
      
      movement = entry.getValue();
    }
    
    return movement;
  }
}