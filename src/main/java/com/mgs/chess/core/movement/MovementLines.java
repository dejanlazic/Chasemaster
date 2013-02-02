package com.mgs.chess.core.movement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mgs.chess.core.PieceType;


public class MovementLines implements Iterable<MovementLine>{
	private List<MovementLine> variants;

	public MovementLines(List<MovementLine> flankVariants) {
		this.variants = flankVariants;
	}

	public List<MovementLine> getVariants(){
		return variants;
	}

	@Override
	public Iterator<MovementLine> iterator() {
		return variants.iterator();
	}

	public List<MovementLine> asList() {
		return variants;
	}

	public List<MovementLine> getRookFlank() {
		return getPieceFlank (PieceType.ROOK);
	}

	public List<MovementLine> getBishopFlank() {
		return getPieceFlank (PieceType.BISHOP);
	}

	public List<MovementLine> getKnightFlank() {
		return getPieceFlank (PieceType.KNIGHT);
	}

	private List<MovementLine> getPieceFlank(PieceType pieceType) {
		List<MovementLine> rookFlank = new ArrayList<MovementLine>();
		for (MovementLine movementLine : variants) {
			if (pieceType.getMovementTypes().contains(movementLine.getType())){
				rookFlank.add(movementLine);
			}
		}
		return rookFlank;
	}
}
