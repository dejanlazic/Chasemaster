package com.mgs.chess.core;

import static com.mgs.chess.core.Location.A1;
import static com.mgs.chess.core.Location.A2;
import static com.mgs.chess.core.Location.A3;
import static com.mgs.chess.core.Location.A4;
import static com.mgs.chess.core.Location.A5;
import static com.mgs.chess.core.Location.A6;
import static com.mgs.chess.core.Location.A7;
import static com.mgs.chess.core.Location.A8;
import static com.mgs.chess.core.Location.B1;
import static com.mgs.chess.core.Location.B7;
import static com.mgs.chess.core.Location.C1;
import static com.mgs.chess.core.Location.C2;
import static com.mgs.chess.core.Location.C7;
import static com.mgs.chess.core.Location.D1;
import static com.mgs.chess.core.Location.D2;
import static com.mgs.chess.core.Location.D7;
import static com.mgs.chess.core.Location.E1;
import static com.mgs.chess.core.Location.E2;
import static com.mgs.chess.core.Location.E7;
import static com.mgs.chess.core.Location.F7;
import static com.mgs.chess.core.Location.G1;
import static com.mgs.chess.core.Location.G2;
import static com.mgs.chess.core.Location.G3;
import static com.mgs.chess.core.Location.G4;
import static com.mgs.chess.core.Location.G5;
import static com.mgs.chess.core.Location.G6;
import static com.mgs.chess.core.Location.G7;
import static com.mgs.chess.core.Location.G8;
import static com.mgs.chess.core.Location.H7;
import static com.mgs.chess.core.Piece.WHITE_KING;
import static com.mgs.chess.core.Piece.WHITE_QUEEN;
import static com.mgs.chess.core.Piece.WHITE_ROOK;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mgs.chess.core.ChessAnaliserImpl;
import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.ChessReader;
import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.Piece;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.movement.Movement;

public class ChessAnaliserImplTest {
	@InjectMocks private ChessAnaliserImpl testObj;
	
	private static final PieceOnLocation WHITE_ROOK_IN_A1 = new PieceOnLocation(WHITE_ROOK, A1);
	private static final PieceOnLocation WHITE_QUEEN_ON_G7 = new PieceOnLocation(WHITE_QUEEN, G7);
	private static final PieceOnLocation WHITE_KING_ON_D1 = new PieceOnLocation(WHITE_KING, D1);
	
	@Mock private ChessBoard boardMock;
	@Mock private ChessBoard checkMateBoardMock;
	@Mock private ChessBoard notCheckMateBoardMock;
	@Mock private ChessReader chessReaderMock;

	
	@BeforeMethod (groups="unit")
	public void setup () {
		testObj = new ChessAnaliserImpl (chessReaderMock);
		MockitoAnnotations.initMocks(this);
	}
	
	@Test (groups="unit")
	public void testFindCheckMateMovement () {
		ChessAnaliserImpl testObjAsSpy = Mockito.spy(testObj);
		
		//Given
		List<PieceOnLocation> whitePieces = Arrays.asList(
			WHITE_ROOK_IN_A1,
			WHITE_QUEEN_ON_G7,
			WHITE_KING_ON_D1
		);
		
		Mockito.doReturn(whitePieces).when(boardMock).getPieces(Color.WHITE);
		Mockito.doReturn(Arrays.asList(A2,A3,A4,A5,A6,A7,A8,B1,C1)).when(testObjAsSpy).findReachableLocations(WHITE_ROOK_IN_A1, boardMock, null);
		Mockito.doReturn(Arrays.asList(C1,C2,D2,E1,E2)).when(testObjAsSpy).findReachableLocations(WHITE_KING_ON_D1, boardMock, null);
		Mockito.doReturn(Arrays.asList(G1,G2,G3,G4,G5,G6,G8,A7,B7,C7,D7,E7,F7,H7)).when(testObjAsSpy).findReachableLocations(WHITE_QUEEN_ON_G7, boardMock, null);
		
		Mockito.when(boardMock.performMovement(Mockito.any(Movement.class))).thenAnswer(new Answer<ChessBoard>() {
			@Override
			public ChessBoard answer(InvocationOnMock invocation) throws Throwable {
				Movement movement = (Movement) invocation.getArguments()[0];
				Piece piece = movement.getMovingPiece();
				Location from = movement.getFrom();
				Location to = movement.getTo();
				                           
				return (piece.equals(WHITE_ROOK) && from == A1 && to == A8 ? checkMateBoardMock : notCheckMateBoardMock);
			}
		});
		
		Mockito.doReturn(true).when(testObjAsSpy).isCheckMate(checkMateBoardMock);
		Mockito.doReturn(false).when(testObjAsSpy).isCheckMate(notCheckMateBoardMock);
		//When
		Movement checkMateMovement = testObjAsSpy.findCheckMateMovement(boardMock, null, Color.WHITE);
		
		//Then
		Assert.assertEquals(checkMateMovement.getMovingPiece(), WHITE_ROOK);
		Assert.assertEquals(checkMateMovement.getFrom(), A1);
		Assert.assertEquals(checkMateMovement.getTo(), A8);
	}
}
