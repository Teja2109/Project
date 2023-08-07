package com.example.BookStore.service;

import com.example.BookStore.entity.Book;
import com.example.BookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;
    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(int Id) {
        return bookRepository.findById(Id).get();
    }

    @Override
    public void deleteById(int Id) {
        bookRepository.deleteById(Id);
    }
}