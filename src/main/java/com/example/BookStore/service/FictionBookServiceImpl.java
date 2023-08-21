package com.example.BookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BookStore.entity.AllBook;
import com.example.BookStore.repository.AllBookRepository;

@Service
public class FictionBookServiceImpl implements FictionBookService {

	@Autowired
	private AllBookRepository allBookRepository;

	public List<AllBook> getAllBooks() {
		return allBookRepository.findByType("fiction");
	}

	@Override
	public AllBook getAllBookById(int Id) {
		return allBookRepository.findById(Id);
	}

}