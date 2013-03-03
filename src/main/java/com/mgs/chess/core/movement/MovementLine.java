package com.mgs.chess.core.movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.SquareContent;
import com.mgs.chess.core.movement.type.MovementType;

public class MovementLine {
	private final Location startingLocation;
	private final List<Location> locations;
	private final MovementType movementType;

	public MovementLine(Location startingLocation, List<Location> locations, MovementType movementType) {
		this.startingLocation = startingLocation;
		if (locations == null){
			this.locations = new ArrayList<Location>();
		}else{
			this.locations = locations;
		}
		this.movementType = movementType;
	}

	public MovementLine(Location startingLocation, Location location, MovementType movementType) {
		this (startingLocation, (location == null ? null : Arrays.asList(location)), movementType);
	}

	public List<Location> getLocations(){
		return locations;
	}

	public Direction getDirection() {
		return movementType.getDirection();
	}

	public List<Location> filterPotentiallyReachablePositions(Color color, ChessBoard board) {
		List<Location> toReturn = new ArrayList<Location>();
		boolean isReachable = false;
		boolean isLastLocation = false;

		for (Location location : getLocations()) {
			SquareContent squareContent = board.getSquareContent(location);
			if (! squareContent.isEmpty()) {
				isLastLocation = true;
				isReachable = false;
				Color toColor = squareContent.asPiece().getColor();
				if (toColor == color.opposite()){
					isReachable = true;
				}
			}else{
				isReachable = true;
				isLastLocation = false;
			}

			if (isReachable) {
				toReturn.add(location);
			}
			if (isLastLocation) break;
		}
		return toReturn;
	}

	public MovementType getType() {
		return movementType;
	}

	public boolean isApplicable(ChessBoard board, Movement previousMovement) {
		if (locations.size() == 0) return false;
		Location locationTo = locations.get(0);
		PieceOnLocation pieceFrom = board.getPieceOnLocation(startingLocation);
		return movementType.isApplicable(pieceFrom, locationTo, board, previousMovement);
	}
}

