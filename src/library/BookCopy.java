package library;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {

    // rep
    private final Book book;
    private Condition condition;
    
    // rep invariant
    // book is immutable
    // condition is either GOOD or DAMAGED
    
    // abstraction function
    // Each book/condition pair gets mapped to a physical copy of a book
    
    // safety from rep exposure argument
    // create a new Book object with the values of book to prevent nullifying ours
    // return a separate reference to condition to keep our copy from being modified
    
    public static enum Condition {
        GOOD, DAMAGED
    };
    
    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) {
        this.book = book;
        this.condition = Condition.GOOD;
        
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert(book != null);
        assert(condition != null);
    }
    
    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        return new Book(book.getTitle(), book.getAuthors(), book.getYear());
    }
    
    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        Condition returnCondition = condition;
        return returnCondition;
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
        checkRep();
    }
    
    @Override
    public String toString() {
        return "BookCopy [book=" + book + ", condition=" + condition + "]";
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
