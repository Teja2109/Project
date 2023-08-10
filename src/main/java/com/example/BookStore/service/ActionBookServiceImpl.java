package com.example.BookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BookStore.entity.AllBook;
import com.example.BookStore.repository.AllBookRepository;

@Service
public class ActionBookServiceImpl implements ActionBookService {

	@Autowired
	private AllBookRepository allBookRepository;

	public List<AllBook> getAllBooks() {
		return allBookRepository.findByType("action");
	}

	@Override
	public AllBook getAllBookById(int Id) {
		// TODO Auto-generated method stub
		return allBookRepository.findById(Id).get();
	}

}