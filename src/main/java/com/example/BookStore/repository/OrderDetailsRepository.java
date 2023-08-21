package com.example.BookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BookStore.entity.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer>{
	
	List<OrderDetails> findByOrderIdAndUserId(int orderid, int id);
	List<OrderDetails> findByOrderId(int orderid);

}