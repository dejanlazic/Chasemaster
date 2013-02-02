package com.mgs.chess.core.movement.condition;


public enum Conditions {
	WHEN_KILLS (new IsKillingCondition ()), 
	WHEN_ON_INITIAL_ROW (new IsOnInitialRowCondition ()), 
	WHEN_NOT_ON_INITIAL_ROW (new IsNotOnInitialRowCondition ()), 
	WHEN_EN_PASSANT (new IsOnEnPassantCondition ()),
	;
	
	private final Condition condition;

	private Conditions(Condition condition) {
		this.condition = condition;
	}
	
	public Condition getCondition() {
		return condition;
	}
}
