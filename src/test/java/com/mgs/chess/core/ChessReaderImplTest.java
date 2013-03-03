package com.mgs.chess.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mgs.chess.core.movement.Direction;
import com.mgs.chess.core.movement.MovementLine;
import com.mgs.chess.core.movement.MovementLines;

public class ChessReaderImplTest {
	private static final Location KING_LOCATION = Location.B5;
	@Spy ChessReaderImpl testObj;
	@Mock private ChessBoard checkBoardMock;
	@Mock private ChessBoard notCheckBoardMock;
	
	@BeforeMethod
	public void setup (){
		testObj = new ChessReaderImpl();
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(notCheckBoardMock.find(Piece.WHITE_KING)).thenReturn(Arrays.asList(Piece.WHITE_KING.on(KING_LOCATION)));
		Mockito.when(checkBoardMock.find(Piece.BLACK_KING)).thenReturn(Arrays.asList(Piece.BLACK_KING.on(KING_LOCATION)));
	}

	@Test
	public void testFindFlanks (){
		MovementLines flanks = testObj.findAllMovementLines(Location.B5);
		
		List<MovementLine> rookVariants = flanks.getRookFlank();
		Assert.assertEquals(rookVariants.size(), 4);
		Assert.assertEquals(rookVariants.get(0).getDirection (), Direction.HORIZONTAL_LEFT);
		Assert.assertEquals(rookVariants.get(0).getLocations (), Arrays.asList(Location.A5));
		Assert.assertEquals(rookVariants.get(1).getDirection (), Direction.HORIZONTAL_RIGHT);
		Assert.assertEquals(rookVariants.get(1).getLocations (), Arrays.asList(Location.C5, Location.D5, Location.E5, Location.F5, Location.G5, Location.H5));
		Assert.assertEquals(rookVariants.get(2).getDirection (), Direction.VERTICAL_FORWARD);
		Assert.assertEquals(rookVariants.get(2).getLocations (), Arrays.asList(Location.B6, Location.B7, Location.B8));
		Assert.assertEquals(rookVariants.get(3).getDirection (), Direction.VERTICAL_BACKWARD);
		Assert.assertEquals(rookVariants.get(3).getLocations (), Arrays.asList(Location.B4, Location.B3, Location.B2, Location.B1));
		
		List<MovementLine> bishopVariants = flanks.getBishopFlank();
		Assert.assertEquals(bishopVariants.size(), 4);
		Assert.assertEquals(bishopVariants.get(0).getDirection(), Direction.DIAGONAL_LEFT_BACKWARD);
		Assert.assertEquals(bishopVariants.get(0).getLocations (), Arrays.asList(Location.A4));
		Assert.assertEquals(bishopVariants.get(1).getDirection(), Direction.DIAGONAL_LEFT_FORWARD);
		Assert.assertEquals(bishopVariants.get(1).getLocations (), Arrays.asList(Location.A6));
		Assert.assertEquals(bishopVariants.get(2).getDirection(), Direction.DIAGONAL_RIGHT_BACKWARD);
		Assert.assertEquals(bishopVariants.get(2).getLocations (), Arrays.asList(Location.C4, Location.D3, Location.E2, Location.F1));
		Assert.assertEquals(bishopVariants.get(3).getDirection(), Direction.DIAGONAL_RIGHT_FORWARD);
		Assert.assertEquals(bishopVariants.get(3).getLocations (), Arrays.asList(Location.C6, Location.D7, Location.E8));
		
		List<MovementLine> knightVariants = flanks.getKnightFlank();
		Assert.assertEquals(knightVariants.size(), 8);
		Assert.assertEquals(knightVariants.get(0).getDirection(), Direction.KNIGHT_1LEFT_2BACKWARD);
		Assert.assertEquals(knightVariants.get(0).getLocations (), Arrays.asList(Location.A3));
		Assert.assertEquals(knightVariants.get(1).getDirection(), Direction.KNIGHT_1LEFT_2FORWARD);
		Assert.assertEquals(knightVariants.get(1).getLocations (), Arrays.asList(Location.A7));
		Assert.assertEquals(knightVariants.get(2).getDirection(), Direction.KNIGHT_2LEFT_1BACKWARD);
		Assert.assertEquals(knightVariants.get(2).getLocations (), new ArrayList<Location>());
		Assert.assertEquals(knightVariants.get(3).getDirection(), Direction.KNIGHT_2LEFT_1FORWARD);
		Assert.assertEquals(knightVariants.get(3).getLocations (), new ArrayList<Location>());
		
		Assert.assertEquals(knightVariants.get(4).getDirection(), Direction.KNIGHT_1RIGHT_2BACKWARD);
		Assert.assertEquals(knightVariants.get(4).getLocations (), Arrays.asList(Location.C3));
		Assert.assertEquals(knightVariants.get(5).getDirection(), Direction.KNIGHT_1RIGHT_2FORWARD);
		Assert.assertEquals(knightVariants.get(5).getLocations (), Arrays.asList(Location.C7));
		Assert.assertEquals(knightVariants.get(6).getDirection(), Direction.KNIGHT_2RIGHT_1BACKWARD);
		Assert.assertEquals(knightVariants.get(6).getLocations (), Arrays.asList(Location.D4));
		Assert.assertEquals(knightVariants.get(7).getDirection(), Direction.KNIGHT_2RIGHT_1FORWARD);
		Assert.assertEquals(knightVariants.get(7).getLocations (), Arrays.asList(Location.D6));
	}
}