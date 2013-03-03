package com.mgs.chess.core.movement.condition;

import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.SquareContent;
import com.mgs.chess.core.movement.Movement;


public class IsKillingCondition implements Condition{

	public boolean isApplicable(PieceOnLocation pieceOnLocation, Location locationTo, ChessBoard board, Movement previousMovement) {
		SquareContent squareContent = board.getSquareContent(locationTo);
		if (squareContent.isEmpty()) return false;
		return pieceOnLocation.getColor().opposite() == squareContent.asPiece().getColor();
	}

}
