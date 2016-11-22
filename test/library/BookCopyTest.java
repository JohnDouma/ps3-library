package library;

import org.junit.Test;

import library.BookCopy.Condition;

import static org.junit.Assert.*;

import java.util.Arrays;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {
    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Testing strategy
     * ==================
     * 
     * Test that a new BookCopy has condition GOOD
     * Test that setCondition updates book condition
     * Test that nullifying book returned from getBook does not modify book
     * Test that modifying condition returned from getCondition does not modify condition
     * Test that two different BookCopy objects with the same book and same condition are not equal
     * 
     */
    
    @Test
    public void testNewBookCopyHasGoodCondition() {
        Book book = new Book("title", Arrays.asList("author"), 1999);
        BookCopy copy = new BookCopy(book);
        assertEquals(Condition.GOOD, copy.getCondition());
        
        copy.setCondition(Condition.DAMAGED);
        assertEquals(Condition.DAMAGED, copy.getCondition());
    }
    
    @Test
    public void testNullifyingBookDoesNotChangeCopy() {
        Book book = new Book("title", Arrays.asList("author"), 1999);
        BookCopy copy = new BookCopy(book);
        book = null;
        book = copy.getBook();
        assertNotNull(book);
    }
    
    @Test
    public void testModifyingReturnedConditionDoesNotModifyCopy() {
        Book book = new Book("title", Arrays.asList("author"), 1999);
        BookCopy copy = new BookCopy(book);
        BookCopy.Condition condition = copy.getCondition();
        condition = null;
        assertNotNull(copy.getCondition());
    }
    
    @Test
    public void testTwoCopiesWithSameAttributesAreDifferent() {
        Book book = new Book("title", Arrays.asList("author"), 1999);
        BookCopy copy1 = new BookCopy(book);
        BookCopy copy2 = new BookCopy(book);
        assertNotEquals(copy1, copy2);
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
