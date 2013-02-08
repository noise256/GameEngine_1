package objectManager;

import junit.framework.Assert;

import org.junit.Test;

public class EntityHashMapTest {
	@Test
	public void testTranslateCoordinates() {
		EntityHashMap e = new EntityHashMap(3, 3);
		
		int[] tc = new int[2];
		
		tc = e.translateCoordinates(25000.0, 25000.0);
		
		Assert.assertEquals(1, tc[0]);
		Assert.assertEquals(1, tc[1]);
		
		tc = e.translateCoordinates(25001.0, 25001.0);
		
		Assert.assertEquals(1, tc[0]);
		Assert.assertEquals(1, tc[1]);
		
		tc = e.translateCoordinates(15000.0, 15000.0);
		
		Assert.assertEquals(0, tc[0]);
		Assert.assertEquals(0, tc[1]);
		
		tc = e.translateCoordinates(16666.0, 16666.0);
		
		Assert.assertEquals(0, tc[0]);
		Assert.assertEquals(0, tc[1]);
		
		tc = e.translateCoordinates(16667.0, 16667.0);
		
		Assert.assertEquals(1, tc[0]);
		Assert.assertEquals(1, tc[1]);
		
		tc = e.translateCoordinates(33333.0, 33333.0);
		
		Assert.assertEquals(1, tc[0]);
		Assert.assertEquals(1, tc[1]);
		
		tc = e.translateCoordinates(33334.0, 33334.0);
		
		Assert.assertEquals(2, tc[0]);
		Assert.assertEquals(2, tc[1]);
		
		tc = e.translateCoordinates(50000.0, 50000.0);
		
		Assert.assertEquals(2, tc[0]);
		Assert.assertEquals(2, tc[1]);
		
		tc = e.translateCoordinates(50001.0, 50001.0);
		
		Assert.assertEquals(2, tc[0]);
		Assert.assertEquals(2, tc[1]);
		
	}
}
