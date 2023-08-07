package com.example.BookStore.service;

import com.example.BookStore.entity.MyBooks;

import java.util.List;

public interface MyBookListService {
	void save(MyBooks book);

	List<MyBooks> getMyAllBooks();

	void deleteById(int Id);
}