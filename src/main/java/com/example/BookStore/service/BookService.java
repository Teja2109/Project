package com.example.BookStore.service;

import com.example.BookStore.entity.Book;

import java.util.List;

public interface BookService {
    void saveBook(Book book);
    List<Book> getAllBooks();

    Book getBookById(int Id);
    void deleteById(int Id);
}