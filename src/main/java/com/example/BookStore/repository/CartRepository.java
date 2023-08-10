package com.example.BookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BookStore.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	public List<Cart> findByUserId(int user_id);

	public Cart findById(int cartid);

	boolean existsByUserIdAndAllBookId(int userId, int bookId);

	public int countByUserId(int userid);

}