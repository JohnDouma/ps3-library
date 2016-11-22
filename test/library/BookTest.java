package library;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     * 
     * TODO: your testing strategy for this ADT should go here.
     * Make sure you have partitions.
     */
    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("This Test Is Just An Example", book.getTitle());
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Testing strategy
     * ==================
     * 
     * Test Book creation with invalid title
     * Test Book creation with empty author list
     * Test Book creation with invalid author
     * Test Book creation with zero year and negative year
     * Try to modify authors list returned from Book#getAuthors
     */
    
    @Test(expected=AssertionError.class)
    public void testBookCreationWithInvalidTitle() {
        new Book("", Arrays.asList("Fred Bloggs"), 2016);
    }
    
    @Test(expected=AssertionError.class)
    public void testBookCreationWithZeroYear() {
        new Book("The Book", Arrays.asList("Fred Bloggs"), 0);
    }
    
    @Test(expected=AssertionError.class)
    public void testBookCreationWithNegativeYear() {
        new Book("The Book", Arrays.asList("Fred Bloggs"), -1);
    }
    
    @Test(expected=AssertionError.class)
    public void testBookCreationWithEmptyAuthorList() {
        new Book("The Book", new ArrayList<String>(), 1990);
    }
    
    @Test(expected=AssertionError.class)
    public void testBookCreationWithInvalidAuthor() {
        new Book("The Book", Arrays.asList("Fred Bloggs", " "), 1990);
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void testTryToModifyAuthorList() {
        Book book = new Book("The Book", Arrays.asList("Fred Bloggs"), 1990);
        book.getAuthors().add("This should throw an exception");
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
