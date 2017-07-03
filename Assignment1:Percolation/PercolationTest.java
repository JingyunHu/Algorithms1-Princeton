import junit.framework.TestCase;

/**
 * A JUnit test case class for the Percolation class.
 */
public class PercolationTest extends TestCase {
    
    /**
     * Test the constructor with illegal args.
     */
    public void testPercolationConstructorWithBadArg() {
        Percolation percolation;
        
        try {
            percolation = new Percolation(0);
            fail();
        } catch (final IllegalArgumentException e) {
            assertEquals("Must enter a value greater than 0. You entered 0.", e.getMessage());
        }
    }
    
    /**
     * Test the constructor with a valid argument.
     */
    public void testPercolationConstructorWithGoodArg() {
        Percolation percolation;
        
        percolation = new Percolation(10);
        assertNotNull("Object should be created", percolation);
    }
    
    /**
     * Try opening a site in the grid and verify that 
     *  - it isn't open before opening and
     *  - is open after opening.
     */
    public void testPercolationOpenSite() {
        Percolation percolation;
        
        percolation = new Percolation(10);
        assertFalse(percolation.isOpen(4, 5));
        percolation.open(4, 5);
        assertTrue(percolation.isOpen(4, 5));
    }
    
    /**
     * 
     */
    public void testPercolationSystemSizeOne() {
        Percolation percolation;
        
        percolation = new Percolation(1);
        assertFalse(percolation.percolates());
        percolation.open(0, 0);
        assertTrue(percolation.percolates());
    }
}
