package com.mgs.chess.core.movement.type;


import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.movement.Direction;
import com.mgs.chess.core.movement.Movement;
import com.mgs.chess.core.movement.condition.Condition;
import com.mgs.chess.core.movement.condition.Conditions;

public enum ConditionalMovementType implements MovementType{	
	DIAGONAL_LEFT_FORWARD_ONE_STEP_TO_KILL (BasicMovementType.DIAGONAL_LEFT_FORWARD_ONE_STEP, Conditions.WHEN_KILLS.getCondition()),
	DIAGONAL_RIGHT_FORWARD_ONE_STEP_TO_KILL (BasicMovementType.DIAGONAL_RIGHT_FORWARD_ONE_STEP, Conditions.WHEN_KILLS.getCondition()),
	DIAGONAL_LEFT_FORWARD_ON_EN_PASSANT (BasicMovementType.DIAGONAL_LEFT_FORWARD_ONE_STEP, Conditions.WHEN_EN_PASSANT.getCondition()),
	DIAGONAL_RIGHT_FORWARD_ON_EN_PASSANT (BasicMovementType.DIAGONAL_RIGHT_FORWARD_ONE_STEP, Conditions.WHEN_EN_PASSANT.getCondition()),
	LINEAL_FORWARD_TWO_STEPS_ON_INITIAL_ROW(BasicMovementType.LINEAL_FORWARD_TWO_STEPS, Conditions.WHEN_ON_INITIAL_ROW.getCondition()),
	LINEAL_FORWARD_ONE_STEP_ON_NOT_INITIAL_ROW(BasicMovementType.LINEAL_FORWARD_ONE_STEP, Conditions.WHEN_NOT_ON_INITIAL_ROW.getCondition())
	;
	
	private final BasicMovementType basicMovementType;
	private final Condition condition;
	
	private ConditionalMovementType(BasicMovementType basicMovementType, Condition condition) {
		this.basicMovementType = basicMovementType;
		this.condition = condition;
	}

	@Override
	public Direction getDirection() {
		return basicMovementType.getDirection();
	}

	@Override
	public DevelopmentType getDevelopmentType() {
		return basicMovementType.getDevelopmentType();
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
		return condition.isApplicable(pieceOnLocation, locationTo, board, previousMovement);
	}
}
