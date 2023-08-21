package com.example.BookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BookStore.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer>{
	public List<Orders> findByUserId(int id);
	public Orders findById(int id);

}