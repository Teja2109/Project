package com.example.BookStore.service;

import java.util.List;

import com.example.BookStore.entity.AllBook;

public interface FictionBookService {

	public List<AllBook> getAllBooks();
	public AllBook getAllBookById(int Id);
}
