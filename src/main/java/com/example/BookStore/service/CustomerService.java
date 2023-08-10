package com.example.BookStore.service;

import java.util.List;

import com.example.BookStore.entity.*;

public interface CustomerService {
	public Customer_details createuser(Customer_details details, String url);

	public boolean checkEmail(String email);

	public Customer_details getUserByUsername(String username);
	
	public Customer_details getDetails();
	
	public void sendVerificationMail(Customer_details details, String url);
	
	public boolean verifyAccount(String code);
}