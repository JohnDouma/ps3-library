package library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // This rep is required! 
    // Do not change the types of inLibrary or checkedOut, 
    // and don't add or remove any other fields.
    // (BigLibrary is where you can create your own rep for
    // a Library implementation.)

    // rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    
    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out

    // TODO: safety from rep exposure argument
    
    public SmallLibrary() {
        inLibrary = new HashSet<BookCopy>();
        checkedOut = new HashSet<BookCopy>();
        
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        for (BookCopy copy: inLibrary) {
            assert(!checkedOut.contains(copy));
        }
    }

    @Override
    public BookCopy buy(Book book) {
        BookCopy copy = new BookCopy(book);
        inLibrary.add(copy);
        checkRep();
        
        return copy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        if (isAvailable(copy)) {
            checkedOut.add(copy);
            inLibrary.remove(copy);
            checkRep();
        }
    }
    
    @Override
    public void checkin(BookCopy copy) {
        if (checkedOut.contains(copy)) {
            inLibrary.add(copy);
            checkedOut.remove(copy);
            checkRep();
        }
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return inLibrary.contains(copy);
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        Set<BookCopy> returnSet = new HashSet<BookCopy>();
        for (BookCopy copy: checkedOut) {
            if (copy.getBook().equals(book)) {
                returnSet.add(copy);
            }
        }
        
        for (BookCopy copy: inLibrary) {
            if (copy.getBook().equals(book)) {
                returnSet.add(copy);
            }
        }
        
        return returnSet;
    }
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
        Set<BookCopy> returnSet = new HashSet<BookCopy>();
        for (BookCopy copy: inLibrary) {
            if (copy.getBook().equals(book)) {
                returnSet.add(copy);
            }
        }
        
        return returnSet;
    }

    @Override
    public List<Book> find(String query) {
        List<Book> books = new ArrayList<Book>();
        if (query.trim().length() == 0) {
            return books;
        }
        
        Set<BookCopy> allCopies = new HashSet<BookCopy>();
        allCopies.addAll(checkedOut);
        allCopies.addAll(inLibrary);
        
        Set<Book> allBooks = new HashSet<Book>();
        for (BookCopy copy: allCopies) {
            allBooks.add(copy.getBook());
        }
        
        List<String> authors;
        String title;
        for (Book book: allBooks) {
            authors = book.getAuthors();
            title = book.getTitle();
            if (title.contains(query)) {
                addBookSortedByDate(book, books);
                continue;
            }
            for (String author: authors) {
                if (author.contains(query)) {
                    addBookSortedByDate(book, books);
                    break;
                }
            }
        }
        
        return books;
    }
    
    @Override
    public void lose(BookCopy copy) {
        if (inLibrary.contains(copy)) {
            inLibrary.remove(copy);
        }
        
        if (checkedOut.contains(copy)) {
            checkedOut.remove(copy);
        }
        
        checkRep();
    }
    
    /*
     * Add book to books.
     * If another book is in books with the same title and author list, make sure the book
     * with the newest publication date appears first.
     */
    private void addBookSortedByDate(final Book book, List<Book> books) {
        int length = books.size();
        int index = 0;
        Book tempBook;
        for (int i = 0; i < length; i++) {
            tempBook = books.get(i);
            if (tempBook.getTitle().equals(book.getTitle()) && tempBook.getAuthors().equals(book.getAuthors())) {
                if (book.getYear() < tempBook.getYear()) {
                    index = i;
                } else {
                    index = i + 1;
                }
                break;
            }
        }
        
        books.add(index, book);
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
