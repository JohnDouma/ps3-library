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
    
    // abstraction function
    // Each book/condition pair gets mapped to a physical copy of a book
    
    // safety from rep exposure argument
    // create a new Book object with the values of book
    
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
    }
    
    // assert the rep invariant
    private void checkRep() {
        throw new RuntimeException("not implemented yet");
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
    }
    
    @Override
    public String toString() {
        return "BookCopy [book=" + book + ", condition=" + condition + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookCopy other = (BookCopy) obj;
        if (book == null) {
            if (other.book != null)
                return false;
        } else if (!book.equals(other.book))
            return false;
        if (condition != other.condition)
            return false;
        return true;
    }
     
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((book == null) ? 0 : book.hashCode());
        result = prime * result + ((condition == null) ? 0 : condition.hashCode());
        return result;
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
