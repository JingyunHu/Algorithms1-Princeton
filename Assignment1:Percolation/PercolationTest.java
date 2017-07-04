/******************************************************************************
 * Run: Open both Percolation.java and PercolationTest.java in DrJava and click
 *      Test.
 *
 *  Unit tests for percolation data type.
 * 
 ******************************************************************************/
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
     * Open a grid with size 1 and verify that it percolates as soon as that 
     * site is opened.
     */
    public void testPercolationSystemSizeOne() {
        Percolation percolation;
        
        percolation = new Percolation(1);
        assertFalse(percolation.percolates());
        percolation.open(1, 1);
        assertTrue(percolation.percolates());
    }
    
    /**
     * Open a grid with size 2 and verify that it percolates as soon as that 
     * site is opened.
     */
    public void testPercolationSystemSizeTwo() {
        Percolation percolation;
        
        percolation = new Percolation(2);
        assertFalse(percolation.percolates());
        percolation.open(1, 1);
        assertFalse(percolation.percolates());
        percolation.open(2, 2);
        assertFalse(percolation.percolates());
        percolation.open(1, 2);
        assertTrue(percolation.percolates());
    }
    
    /**
     * Test the {@code open} method with illegal args.
     */
    public void testPercolationOpenWithOutOfBound() {
        Percolation percolation;
        
        percolation = new Percolation(2);
        try {
            percolation.open(2, 0);
            fail();
        } catch (final IndexOutOfBoundsException e) {
            assertEquals("row/column (2/0) out of bounds (1 - 2).", e.getMessage());
        }
    }
}
