package library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * BigLibrary represents a large collection of books that might be held by a city or
 * university library system -- millions of books.
 * 
 * In particular, every operation needs to run faster than linear time (as a function of the number of books
 * in the library).
 */
public class BigLibrary implements Library {

    // TODO: rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    private Map<String, Set<Book>> titleToBooks;
    private Map<String, Set<Book>> authorToBooks;
    private Map<Book, Set<BookCopy>> bookToBookCopies;
    
    // TODO: rep invariant
    //    the intersection of inLibrary and checkedOut is the empty set
    
    // TODO: abstraction function
    
    // TODO: safety from rep exposure argument
    
    public BigLibrary() {
        inLibrary = new HashSet<BookCopy>();
        checkedOut = new HashSet<BookCopy>();
        titleToBooks = new HashMap<String, Set<Book>>();
        authorToBooks = new HashMap<String, Set<Book>>();
        bookToBookCopies = new HashMap<Book, Set<BookCopy>>();
        
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
        final BookCopy copy = new BookCopy(book);
        inLibrary.add(copy);
        
        if (!bookToBookCopies.containsKey(book)) {
            bookToBookCopies.put(book, new HashSet<BookCopy>());
            
            // This is a new book; populate titleToBooks and authorToBooks
            final String title = book.getTitle();
            if (!titleToBooks.containsKey(title)) {
                titleToBooks.put(title, new HashSet<Book>());
            }
            titleToBooks.get(title).add(book);
            
            final List<String> authors = book.getAuthors();
            for (String author: authors) {
                if (!authorToBooks.containsKey(author)) {
                    authorToBooks.put(author, new HashSet<Book>());
                }
                authorToBooks.get(author).add(book);
            }
        }
        bookToBookCopies.get(book).add(copy);
        
        checkRep();
        
        return copy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        if (inLibrary.contains(copy)) {
            inLibrary.remove(copy);
            checkedOut.add(copy);
            checkRep();
        }
    }
    
    @Override
    public void checkin(BookCopy copy) {
        if (checkedOut.contains(copy)) {
            checkedOut.remove(copy);
            inLibrary.add(copy);
            checkRep();
        }
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        final Set<BookCopy> copies = new HashSet<BookCopy>();
        final Set<BookCopy> allCopies = bookToBookCopies.get(book);
        if (allCopies != null) {
            copies.addAll(bookToBookCopies.get(book));
        }
        
        return copies;
    }

    @Override
    public Set<BookCopy> availableCopies(Book book) {
        final Set<BookCopy> copies = new HashSet<BookCopy>();
        final Set<BookCopy> allCopies = bookToBookCopies.get(book);
        if (allCopies == null) {
            return copies;
        }
        for (BookCopy copy: allCopies) {
            if (inLibrary.contains(copy)) {
                copies.add(copy);
            }
        }
        return copies;
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return inLibrary.contains(copy);
    }
    
    @Override
    public List<Book> find(String query) {
        List<Book> books = new ArrayList<Book>();
        if (query.trim().length() == 0) {
            return books;
        }
        
        for (String title: titleToBooks.keySet()) {
            if (title.contains(query) || query.contains(title)) {
                Set<Book> titleBooks = titleToBooks.get(title);
                for (Book book: titleBooks) {
                    if (!books.contains(book)) {
                        addBookSortedByDate(book, books);
                    }
                }
            }
        }
        
        for (String author: authorToBooks.keySet()) {
            if (author.contains(query) || query.contains(author)) {
                Set<Book> authorBooks = authorToBooks.get(author);
                for (Book book: authorBooks) {
                    if (!books.contains(book)) {
                        addBookSortedByDate(book, books);
                    }
                }
            }
        }
        
        return books;
    }
    
    @Override
    public void lose(BookCopy copy) {
        final Book book = copy.getBook();
        final String title = book.getTitle();
        final List<String> authors = book.getAuthors();
        
        if (inLibrary.contains(copy)) {
            inLibrary.remove(copy);
        }
        
        if (checkedOut.contains(copy)) {
            checkedOut.remove(copy);
        }
        
        Set<BookCopy> copies = bookToBookCopies.get(book);
        copies.remove(copy);
        if (copies.size() == 0) {
            bookToBookCopies.remove(book);
            titleToBooks.get(title).remove(book);
            for (String author: authors) {
                authorToBooks.get(author).remove(book);
            }
        }
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
            if (tempBook.getTitle().equals(book.getTitle()) && tempBook.getAuthors().equals(book.getAuthors()) && book.getYear() > tempBook.getYear()) {
                break;
            }
            
            index++;
        }
        
        books.add(index, book);
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
