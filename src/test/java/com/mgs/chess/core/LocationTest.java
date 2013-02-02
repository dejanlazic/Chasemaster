package com.mgs.chess.core;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mgs.chess.core.Location;

public class LocationTest {
	@Test
	public void testCoordinates (){
		Assert.assertEquals(Location.A1.getCoordinateX(), 1);
		Assert.assertEquals(Location.A1.getCoordinateY(), 1);
		
		Assert.assertEquals(Location.C4.getCoordinateX(), 3);
		Assert.assertEquals(Location.C4.getCoordinateY(), 4);
		
		Assert.assertEquals(Location.H2.getCoordinateX(), 8);
		Assert.assertEquals(Location.H2.getCoordinateY(), 2);
	}
}