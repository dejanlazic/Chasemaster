package com.mgs.chess.core;

import java.util.ArrayList;
import java.util.List;

import com.mgs.chess.core.movement.MovementLine;
import com.mgs.chess.core.movement.MovementLines;
import com.mgs.chess.core.movement.type.DevelopmentType;
import com.mgs.chess.core.movement.type.MovementType;

public class ChessReaderImpl implements ChessReader {
	public boolean isThreathening(ChessBoard board, PieceOnLocation piece, Location toThreadLocation) {
		return false;
	}

	@Override
	public MovementLines findAllMovementLines(Location location) {
		List<MovementLine> lines = new ArrayList<MovementLine>();
		lines.addAll(findMovementLines (Piece.WHITE_KNIGHT.on(location)).asList());
		lines.addAll(findMovementLines (Piece.WHITE_ROOK.on(location)).asList());
		lines.addAll(findMovementLines (Piece.WHITE_BISHOP.on(location)).asList());
		return new MovementLines(lines);
	}
	
	@Override
	public MovementLines findMovementLines(PieceOnLocation pieceOnLocation) {
		List<MovementLine> movementLines = new ArrayList<MovementLine>();
		for (MovementType movementType : pieceOnLocation.getType().getMovementTypes()) {
			movementLines.add(findMovementLine (pieceOnLocation.getLocation(), pieceOnLocation.getColor(), movementType));
		};
		
		return new MovementLines(movementLines);
	}

	private MovementLine findMovementLine(Location location, Color color, MovementType movementType) {
		List<Location> locations = new ArrayList<Location> ();
		int deltaX = movementType.getDeltaX ();
		int deltaY = movementType.getDeltaY (color);
		
		Location nextLocation = location.add (deltaX, deltaY);
		locations.add(nextLocation);
		if ((movementType.getDevelopmentType() == DevelopmentType.ONE_STEP) || (nextLocation == null)){
			return new MovementLine(location, nextLocation, movementType);
		}else {
			nextLocation = nextLocation.add (deltaX, deltaY);
			if (nextLocation!=null){
				locations.add(nextLocation);
				if (movementType.getDevelopmentType() == DevelopmentType.TWO_STEPS){
					return new MovementLine(location, locations, movementType);
				}else{
					nextLocation = nextLocation.add (deltaX, deltaY);
					while (nextLocation != null){
						locations.add(nextLocation);
						nextLocation = nextLocation.add (deltaX, deltaY);
					}
				}
			}
			return new MovementLine(location, locations, movementType);
		}
	}

	@Override
	public boolean containsPieceWithColor(Location location, Color color, ChessBoard board) {
		return board.getSquareContent(location).getContentColor() == color;
	}
}
