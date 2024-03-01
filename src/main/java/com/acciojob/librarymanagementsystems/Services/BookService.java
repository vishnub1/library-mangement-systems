package com.acciojob.librarymanagementsystems.Services;

import com.acciojob.librarymanagementsystems.Entities.Author;
import com.acciojob.librarymanagementsystems.Entities.Book;
import com.acciojob.librarymanagementsystems.Exceptions.InvalidPageCountException;
import com.acciojob.librarymanagementsystems.Repositories.AuthorRepository;
import com.acciojob.librarymanagementsystems.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public String addBook(Book book) throws Exception {

        if(book.getNoOfPages()<=0) {
            throw new InvalidPageCountException("Page Count entered is incorrect");
        }
        bookRepository.save(book);
        return "Book has been saved to the DB";
    }

    public String associateBookAndAuthor(Integer bookId,Integer authorId) {

        Book book = bookRepository.findById(bookId).get();
        Author author = authorRepository.findById(authorId).get();

        //associate book and author Entity
        book.setAuthor(author);
        author.setNoOfBooks(author.getNoOfBooks()+1);

        bookRepository.save(book);
        authorRepository.save(author);
        return "Book and Author have been associated";
    }

    public List<Book> findBooksByAuthor(Integer authorId) {

        List<Book> allBooks = bookRepository.findAll();
        //I need to iterate where :
        //Book.getAuthor.getId is matching
        List<Book> ansList = new ArrayList<>();

        for(Book book:allBooks) {
            if(book.getAuthor().getAuthorId().equals(authorId)){
                ansList.add(book);
            }
        }
        return ansList;
    }





}