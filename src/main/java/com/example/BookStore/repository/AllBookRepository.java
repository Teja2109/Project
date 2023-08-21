package com.example.BookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.BookStore.entity.AllBook;

@Repository
public interface AllBookRepository extends JpaRepository<AllBook, Integer> {
	public List<AllBook> findByType(String type);
	public AllBook findById(int id);
}
