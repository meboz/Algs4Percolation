import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PercolationTests{
    private void AssertAllSitesClosed(int N, Percolation p) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                assertFalse(p.isOpen(i, j));
            }
        }
    }

    @Test
    public void PercolationCreationTest() {
        Percolation p = new Percolation(5);
        assertNotNull(p);
    }

    @Test
    public void ANewPercolation_ShouldHaveAllClosedOpen() {
        Percolation p = new Percolation(3);
        AssertAllSitesClosed(3, p);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void WhenInspectingIsOpen_GivenRowIsOutOfBounds_ThenShouldThrowOutOfboundsException()
            throws Exception {
        Percolation p = new Percolation(3);
        p.open(4, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void WhenInspectingIsOpen_GivenRowIsOutOfLowerBounds_ThenShouldThrowOutOfBoundsException()
            throws Exception {
        Percolation p = new Percolation(3);
        p.open(-1, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void WhenInspectingIsOpen_GivenColumnIsOutOfUpperBounds_ThenShouldThrowOutOfBoundsException()
            throws Exception {
        Percolation p = new Percolation(3);
        p.open(1, 4);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void WhenInspectingIsOpen_GivenColumnIsOutOfLowerBounds_ThenShouldThrowOutOfBoundsException()
            throws Exception {
        Percolation p = new Percolation(3);
        p.open(1, 0);
    }

    @Test
    public void WhenOpeningASite_ShouldMarkSiteAsOpen() {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 2);

        assertFalse(p.isOpen(1, 2));
        assertTrue(p.isOpen(1, 1));
        assertTrue(p.isOpen(2, 2));

    }

    @Test
    public void Simple2x2Percolation() {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 1);
        assertTrue(p.percolates());

        p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 2);
        assertFalse(p.percolates());

        p.open(2, 1);

        assertTrue(p.percolates());
    }

    @Test
    public void OpenPredeterminedSites_WhenNIs1() {
        Percolation p = new Percolation(1);
        p.open(1, 1);

        assertTrue(p.isOpen(1, 1));
        assertTrue(p.percolates());
    }
}
