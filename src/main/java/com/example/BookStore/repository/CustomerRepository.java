package com.example.BookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.example.BookStore.entity.*;

@Repository
public interface CustomerRepository extends JpaRepository<Customer_details, Integer> {

	public boolean existsByEmail(String email);

	public Customer_details findByEmail(String email);
	
	public Customer_details findByEmailAndPhoneno(String email, String phoneno);
	
	public Customer_details findByVerificationCode(String code);
	
	public String getPasswordById(int id);
	
	public Customer_details findById(int id);
}