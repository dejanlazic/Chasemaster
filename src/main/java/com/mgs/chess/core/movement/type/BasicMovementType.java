package com.mgs.chess.core.movement.type;

import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.movement.Direction;
import com.mgs.chess.core.movement.Movement;

public enum BasicMovementType implements MovementType{
	LINEAL_FORWARD_MANY_STEPS (Direction.VERTICAL_FORWARD, DevelopmentType.MANY_STEPS),
	LINEAL_BACKWARD_MANY_STEPS (Direction.VERTICAL_BACKWARD, DevelopmentType.MANY_STEPS),
	LINEAL_LEFT_MANY_STEPS (Direction.HORIZONTAL_LEFT, DevelopmentType.MANY_STEPS),
	LINEAL_RIGHT_MANY_STEPS (Direction.HORIZONTAL_RIGHT, DevelopmentType.MANY_STEPS),
	
	DIAGONAL_LEFT_FORWARD_MANY_STEPS (Direction.DIAGONAL_LEFT_FORWARD, DevelopmentType.MANY_STEPS),
	DIAGONAL_RIGHT_FORWARD_MANY_STEPS (Direction.DIAGONAL_RIGHT_FORWARD, DevelopmentType.MANY_STEPS),
	DIAGONAL_RIGHT_BACKWARD_MANY_STEPS (Direction.DIAGONAL_RIGHT_BACKWARD, DevelopmentType.MANY_STEPS),
	DIAGONAL_LEFT_BACKWARD_MANY_STEPS (Direction.DIAGONAL_LEFT_BACKWARD, DevelopmentType.MANY_STEPS),
	
	LINEAL_FORWARD_ONE_STEP (Direction.VERTICAL_FORWARD, DevelopmentType.ONE_STEP),
	LINEAL_BACKWARD_ONE_STEP (Direction.VERTICAL_BACKWARD, DevelopmentType.ONE_STEP),
	LINEAL_LEFT_ONE_STEP (Direction.HORIZONTAL_LEFT, DevelopmentType.ONE_STEP),
	LINEAL_RIGHT_ONE_STEP (Direction.HORIZONTAL_RIGHT, DevelopmentType.ONE_STEP),
	
	DIAGONAL_LEFT_FORWARD_ONE_STEP (Direction.DIAGONAL_LEFT_FORWARD, DevelopmentType.ONE_STEP),
	DIAGONAL_RIGHT_FORWARD_ONE_STEP (Direction.DIAGONAL_RIGHT_FORWARD, DevelopmentType.ONE_STEP),
	DIAGONAL_RIGHT_BACKWARD_ONE_STEP (Direction.DIAGONAL_LEFT_BACKWARD, DevelopmentType.ONE_STEP),
	DIAGONAL_LEFT_BACKWARD_ONE_STEP (Direction.DIAGONAL_RIGHT_BACKWARD, DevelopmentType.ONE_STEP),
	
	KNIGHT_1LEFT_2BACKWARD (Direction.KNIGHT_1LEFT_2BACKWARD, DevelopmentType.ONE_STEP),
	KNIGHT_1LEFT_2FORWARD (Direction.KNIGHT_1LEFT_2FORWARD, DevelopmentType.ONE_STEP),
	KNIGHT_2LEFT_1BACKWARD (Direction.KNIGHT_2LEFT_1BACKWARD, DevelopmentType.ONE_STEP),
	KNIGHT_2LEFT_1FORWARD (Direction.KNIGHT_2LEFT_1FORWARD, DevelopmentType.ONE_STEP),
	KNIGHT_1RIGHT_2BACKWARD (Direction.KNIGHT_1RIGHT_2BACKWARD, DevelopmentType.ONE_STEP),
	KNIGHT_1RIGHT_2FORWARD (Direction.KNIGHT_1RIGHT_2FORWARD, DevelopmentType.ONE_STEP),
	KNIGHT_2RIGHT_1BACKWARD (Direction.KNIGHT_2RIGHT_1BACKWARD, DevelopmentType.ONE_STEP),
	KNIGHT_2RIGHT_1FORWARD (Direction.KNIGHT_2RIGHT_1FORWARD, DevelopmentType.ONE_STEP), 
	LINEAL_FORWARD_TWO_STEPS(Direction.VERTICAL_FORWARD, DevelopmentType.TWO_STEPS)
	;
	
	private final Direction flankLineType;
	private final DevelopmentType developmentType;
	
	private BasicMovementType(Direction flankLineType, DevelopmentType developmentType) {
		this.flankLineType = flankLineType;
		this.developmentType = developmentType;
	}

	@Override
	public Direction getDirection() {
		return flankLineType;
	}

	@Override
	public DevelopmentType getDevelopmentType() {
		return developmentType;
	}

	@Override
	public int getDeltaX() {
		return getDirection().getDeltaX();
	}
	
	@Override
	public int getDeltaY(Color forColor) {
		return getDirection().getDeltaY(forColor);
	}

	@Override
	public boolean isApplicable(PieceOnLocation pieceOnLocation, Location locationTo, ChessBoard board, Movement previousMovement) {
		return true;
	}
}