package library;

import java.util.Collections;
import java.util.List;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

    //rep
    private final String title;
    private final List<String> authors;
    private final int year;
    
    // Rep invariants
    // Title of the book must contain at least one non-space character.
    // Each book must contain at least one author.
    // Each author's name must contain at least one non-space character.
    // The year must be a year in the conventional calendar
    
    // Abstraction function
    // The title, list of authors and publication year map to an edition of a book, not the
    // physical book.
    
    // Safety from rep exposure argument
    // The title is a String which is immutable. The year is a primitive int so no modification
    // to a returned value of the year can effect the internal value of this.year. Also, year
    // is declared to be final so it cannot be changed once it is set.
    // The list of authors returned by getAuthors is an unmodifiable list so it cannot be used
    // to modify our copy of the list of authors.
    
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
        this.title = title;
        this.authors = authors;
        this.year = year;
        
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert(title.trim().length() > 0);
        assert(authors.size() > 0);
        for (String author: authors) {
            assert(author.trim().length() > 0);
        }
        assert(year > 0);
    }
    
    /**
     * @return the title of this book
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return year;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    public String toString() {
        throw new RuntimeException("not implemented yet");
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
    @Override
    public boolean equals(Object that) {
         throw new RuntimeException("not implemented yet");
     }
     
     @Override
     public int hashCode() {
         throw new RuntimeException("not implemented yet");
     }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
