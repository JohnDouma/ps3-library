package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import library.BookCopy.Condition;

/**
 * Test suite for Library ADT.
 */
@RunWith(Parameterized.class)
public class LibraryTest {

    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

    /**
     * Implementation classes for the Library ADT.
     * JUnit runs this test suite once for each class name in the returned array.
     * @return array of Java class names, including their full package prefix
     */
    @Parameters(name="{0}")
    public static Object[] allImplementationClassNames() {
        return new Object[] { 
            "library.SmallLibrary", 
            "library.BigLibrary"
        }; 
    }

    /**
     * Implementation class being tested on this run of the test suite.
     * JUnit sets this variable automatically as it iterates through the array returned
     * by allImplementationClassNames.
     */
    @Parameter
    public String implementationClassName;    

    /**
     * @return a fresh instance of a Library, constructed from the implementation class specified
     * by implementationClassName.
     */
    public Library makeLibrary() {
        try {
            Class<?> cls = Class.forName(implementationClassName);
            return (Library) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /*
     * Testing strategy
     * ==================
     *
     * Test buyBook; make sure the condition is Condition.GOOD
     * Test checkout to make sure copy is no longer available
     * Test isAvailable to make sure copy is available after purchase and unavailable after checkout
     * Test allCopies with no copies in the library
     * Test allCopies with both checked-out and available copies
     * Test allAvailableCopies to with no available copies and some available copies
     * Test find with multiple copies of the same book
     * Test find with no books found - allow empty list as well as null list
     * Test lose with books that are available and books that are checked out
     */
    
    @Test
    public void testBuyBook() {
        Book book = new Book("title", Arrays.asList("author"), 2000);
        Library library = makeLibrary();
        BookCopy copy = library.buy(book);
        assertTrue(library.isAvailable(copy));
        assertEquals(Condition.GOOD, copy.getCondition());
        library.checkout(copy);
        assertFalse(library.isAvailable(copy));
    }
    
    @Test
    public void testAllCopiesEmptyLibrary() {
        Library library = makeLibrary();
        Book book = new Book("title", Arrays.asList("author"), 2000);
        Set<BookCopy> copies = library.allCopies(book);
        
        assertTrue(copies == null || copies.size() == 0);
    }
    
    @Test
    public void testAllCopiesMultipleCopies() {
        Library library = makeLibrary();
        Book book1 = new Book("title", Arrays.asList("author"), 2000);
        Book book2 = new Book("TITLE", Arrays.asList("Fred Bloggs"), 1992);
        library.buy(book1);
        library.buy(book1);
        library.buy(book2);
        
        Set<BookCopy> copies = library.allCopies(book1);
        
        assertEquals(2, copies.size());
    }
    
    @Test
    public void testAvailableCopies() {
        Library library = makeLibrary();
        Book book1 = new Book("title", Arrays.asList("author"), 2000);
        Book book2 = new Book("TITLE", Arrays.asList("Fred Bloggs"), 1992);
        BookCopy copy1 = library.buy(book1);
        BookCopy copy2 = library.buy(book1);
        BookCopy copy3 = library.buy(book2);
        
        library.checkout(copy1);
        
        Set<BookCopy> copies = library.availableCopies(book1);
        
        assertEquals(1, copies.size());
        
        library.checkout(copy3);
        
        copies = library.availableCopies(book2);
        
        assertEquals(0, copies.size());
        
        library.checkin(copy1);
        
        copies = library.availableCopies(book1);
        
        assertEquals(2, copies.size());
    }
    
    @Test
    public void testLose() {
        Library library = makeLibrary();
        Book book1 = new Book("title", Arrays.asList("author"), 2000);
        Book book2 = new Book("TITLE", Arrays.asList("Fred Bloggs"), 1992);
        BookCopy copy1 = library.buy(book1);
        BookCopy copy2 = library.buy(book1);
        BookCopy copy3 = library.buy(book2);
        
        library.lose(copy1);
        
        Set<BookCopy> copies = library.allCopies(book1);
        
        assertEquals(1, copies.size());
        
        library.checkout(copy3);
        
        library.lose(copy3);
        
        copies = library.allCopies(book2);
        
        assertEquals(0, copies.size());
    }
    
    @Test
    public void testExampleTest() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals(Collections.emptySet(), library.availableCopies(book));
    }
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
