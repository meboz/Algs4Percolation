import static org.junit.Assert.*;

import org.junit.Test;


public class PercolationStatsTests {

	@Test
	public void GivenASquareNumber_ShouldCalculateRowFromInteger() {
		assertEquals(PercolationStats.RowIdFor(4,3), 2);
		assertEquals(PercolationStats.RowIdFor(3,3), 1);
		assertEquals(PercolationStats.RowIdFor(6,3), 2);
		assertEquals(PercolationStats.RowIdFor(9,3), 3);
		assertEquals(PercolationStats.RowIdFor(7,5), 2);
	}

	@Test
	public void GivenASquareNumber_ShouldCalculateColumnFromInteger() {
		assertEquals(1, PercolationStats.ColIdFor(1,3));
		assertEquals(2, PercolationStats.ColIdFor(2,3));
		assertEquals(3, PercolationStats.ColIdFor(3,3));
		assertEquals(1, PercolationStats.ColIdFor(4,3));
		assertEquals(2, PercolationStats.ColIdFor(5,3));
		assertEquals(3, PercolationStats.ColIdFor(6,3));
	}
}
