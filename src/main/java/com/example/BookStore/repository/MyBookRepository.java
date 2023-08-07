package com.example.BookStore.repository;

import com.example.BookStore.entity.MyBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBookRepository extends JpaRepository<MyBooks, Integer> {

}