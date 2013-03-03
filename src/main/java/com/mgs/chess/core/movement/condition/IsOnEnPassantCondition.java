package com.mgs.chess.core.movement.condition;

import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.PieceType;
import com.mgs.chess.core.movement.Movement;

public class IsOnEnPassantCondition implements Condition {

	@Override
	public boolean isApplicable(PieceOnLocation pieceOnLocation, Location locationTo, ChessBoard board, Movement previousMovement) {
		if (previousMovement == null) return false;
		if (previousMovement.getColor() == pieceOnLocation.getColor()) return false;
		if (previousMovement.getMovingPiece().getType() != PieceType.PAWN) return false;
		if (previousMovement.getFrom().getCoordinateY() != previousMovement.getColor().getInitialRowFor(PieceType.PAWN)) return false;
		if (Math.abs(previousMovement.getTo().getCoordinateY() - previousMovement.getFrom().getCoordinateY()) != 2) return false;
		if (previousMovement.getTo().getCoordinateX() == locationTo.getCoordinateX()) return true;
		
		return false;
	}

}
