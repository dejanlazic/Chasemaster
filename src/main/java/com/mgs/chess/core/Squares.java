package com.mgs.chess.core;


public class Squares{
	private final SquareContent[][] values;

	public Squares() {
		this (buildEmptySquareContentArray());
	}

	private static SquareContent[][] buildEmptySquareContentArray() {
		SquareContent[][] squareContents = new SquareContent[8][8];
		for (int i=1; i<=8 ; i++){
			for (int j=1; j<=8; j++){
				squareContents [i-1][j-1] = SquareContent.EMPTY_SQUARE;
			}
		}
		return squareContents;
	}
	
	public Squares(SquareContent[][] values) {
		this.values = values;
	}

	public Squares copy() {
		SquareContent[][] copy = buildEmptySquareContentArray();
		
		int currentRow = 0;
		for (SquareContent[] rowValues : values) {
			int currentColumn = 0;
			for (SquareContent squareValue : rowValues) {
				copy[currentRow][currentColumn++]=squareValue;
			}
			currentRow++;
		}
		
		return new Squares(copy);
	}

	public void setContent(SquareContent piece, int coordinateX, int coordinateY) {
		values[coordinateX-1][coordinateY-1] = piece;
	}

	public SquareContent getContent(int coordinateX, int coordinateY) {
		return values[coordinateX-1][coordinateY-1];
	}
}