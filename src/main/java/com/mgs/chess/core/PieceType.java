package com.mgs.chess.core;

import java.util.Arrays;
import java.util.List;

import com.mgs.chess.core.movement.type.BasicMovementType;
import com.mgs.chess.core.movement.type.ConditionalMovementType;
import com.mgs.chess.core.movement.type.MovementType;

public enum PieceType {
	ROOK (
		BasicMovementType.LINEAL_LEFT_MANY_STEPS,
		BasicMovementType.LINEAL_RIGHT_MANY_STEPS,
		BasicMovementType.LINEAL_FORWARD_MANY_STEPS,
		BasicMovementType.LINEAL_BACKWARD_MANY_STEPS
	), 
	QUEEN (
		BasicMovementType.LINEAL_LEFT_MANY_STEPS,
		BasicMovementType.LINEAL_RIGHT_MANY_STEPS,
		BasicMovementType.LINEAL_FORWARD_MANY_STEPS,
		BasicMovementType.LINEAL_BACKWARD_MANY_STEPS,
		BasicMovementType.DIAGONAL_LEFT_BACKWARD_MANY_STEPS,
		BasicMovementType.DIAGONAL_LEFT_FORWARD_MANY_STEPS,
		BasicMovementType.DIAGONAL_RIGHT_BACKWARD_MANY_STEPS,
		BasicMovementType.DIAGONAL_RIGHT_FORWARD_MANY_STEPS
	), 
	BISHOP(
		BasicMovementType.DIAGONAL_LEFT_BACKWARD_MANY_STEPS,
		BasicMovementType.DIAGONAL_LEFT_FORWARD_MANY_STEPS,
		BasicMovementType.DIAGONAL_RIGHT_BACKWARD_MANY_STEPS,
		BasicMovementType.DIAGONAL_RIGHT_FORWARD_MANY_STEPS
	),	
	KING(
		BasicMovementType.LINEAL_LEFT_ONE_STEP,
		BasicMovementType.LINEAL_RIGHT_ONE_STEP,
		BasicMovementType.LINEAL_FORWARD_ONE_STEP,
		BasicMovementType.LINEAL_BACKWARD_ONE_STEP,
		BasicMovementType.DIAGONAL_LEFT_BACKWARD_ONE_STEP,
		BasicMovementType.DIAGONAL_LEFT_FORWARD_ONE_STEP,
		BasicMovementType.DIAGONAL_RIGHT_BACKWARD_ONE_STEP,
		BasicMovementType.DIAGONAL_RIGHT_FORWARD_ONE_STEP
	),  
	KNIGHT(
		BasicMovementType.KNIGHT_1LEFT_2BACKWARD,
		BasicMovementType.KNIGHT_1LEFT_2FORWARD,
		BasicMovementType.KNIGHT_2LEFT_1BACKWARD,
		BasicMovementType.KNIGHT_2LEFT_1FORWARD,
		BasicMovementType.KNIGHT_1RIGHT_2BACKWARD,
		BasicMovementType.KNIGHT_1RIGHT_2FORWARD,
		BasicMovementType.KNIGHT_2RIGHT_1BACKWARD,
		BasicMovementType.KNIGHT_2RIGHT_1FORWARD
	), 
	PAWN(
		ConditionalMovementType.LINEAL_FORWARD_ONE_STEP_ON_NOT_INITIAL_ROW,
		ConditionalMovementType.DIAGONAL_LEFT_FORWARD_ONE_STEP_TO_KILL,
		ConditionalMovementType.DIAGONAL_RIGHT_FORWARD_ONE_STEP_TO_KILL,
		ConditionalMovementType.LINEAL_FORWARD_TWO_STEPS_ON_INITIAL_ROW,
		ConditionalMovementType.DIAGONAL_LEFT_FORWARD_ON_EN_PASSANT,
		ConditionalMovementType.DIAGONAL_RIGHT_FORWARD_ON_EN_PASSANT
	),
	;

	private final List<MovementType> availableMovementTypes;
	
	private PieceType(MovementType...movementTypes ) {
		this.availableMovementTypes = Arrays.asList(movementTypes);
	}
	
	public Piece withColor(Color color) {
		return Piece.with (this, color);
	}

	public List<MovementType> getMovementTypes() {
		return availableMovementTypes;
	}
}
