package com.example.BookStore.service;

import com.example.BookStore.entity.MyBooks;
import com.example.BookStore.repository.MyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBookListServiceImpl implements MyBookListService{

    @Autowired
    private MyBookRepository myBookRepository;
    @Override
    public void save(MyBooks book) {
        myBookRepository.save(book);
    }

    @Override
    public List<MyBooks> getMyAllBooks() {
        return myBookRepository.findAll();
    }

    @Override
    public void deleteById(int Id) {
        myBookRepository.deleteById(Id);
    }
}