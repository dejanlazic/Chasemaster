package com.mgs.chess.core;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.Piece;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.PieceType;
import com.mgs.chess.core.SquareContent;

public class ChessBoardTest {
	private ChessBoard testObj;
	
	@BeforeMethod(groups="unit")
	public void setup () {
		testObj = new ChessBoard().
			addPiece(Piece.WHITE_ROOK, 	Location.A1).
			addPiece(Piece.WHITE_KING, 	Location.D1).
			addPiece(Piece.WHITE_QUEEN, Location.G7).
			addPiece(Piece.BLACK_KING, 	Location.H4);
	}
	
	@Test(groups="unit")
	public void testGetSquareContent() {
		Assert.assertEquals(testObj.getSquareContent(Location.A8), SquareContent.EMPTY_SQUARE);
		Assert.assertEquals(testObj.getSquareContent(Location.A1), Piece.WHITE_ROOK);
	}
	
	@Test(groups="unit")
	public void testPerformMovement() {
		ChessBoard newBoard = testObj.performMovement(Piece.WHITE_ROOK, Location.A1, Location.A8);
		
		Assert.assertEquals(testObj.getSquareContent(Location.A1), Piece.WHITE_ROOK);
		Assert.assertEquals(testObj.getSquareContent(Location.A8), SquareContent.EMPTY_SQUARE);
		
		Assert.assertEquals(newBoard.getSquareContent(Location.A1), SquareContent.EMPTY_SQUARE);
		Assert.assertEquals(newBoard.getSquareContent(Location.A8), Piece.WHITE_ROOK);
	}
	
	@Test
	public void testGetPieces () {
		List<PieceOnLocation> whitePieces = testObj.getPieces(Color.WHITE);
		List<PieceOnLocation> blackPieces = testObj.getPieces(Color.BLACK);
		
		Assert.assertEquals(whitePieces.size(), 3);
		Assert.assertEquals(whitePieces.get(0), Piece.WHITE_ROOK.on(Location.A1));
		Assert.assertEquals(whitePieces.get(1), Piece.WHITE_KING.on(Location.D1));
		Assert.assertEquals(whitePieces.get(2), Piece.WHITE_QUEEN.on(Location.G7));
		
		Assert.assertEquals(blackPieces.size(), 1);
		Assert.assertEquals(blackPieces.get(0), Piece.BLACK_KING.on(Location.H4));
	}
	
	@Test
	public void testFindPiece () {
		List<PieceOnLocation> whiteRooks = testObj.find(Piece.WHITE_ROOK);
		
		Assert.assertEquals(whiteRooks.size(), 1);
		Assert.assertEquals(whiteRooks.get(0).getType(), PieceType.ROOK);
		Assert.assertEquals(whiteRooks.get(0).getLocation(), Location.A1);
	}
}
