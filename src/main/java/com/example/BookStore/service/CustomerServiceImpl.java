package com.example.BookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.BookStore.entity.*;
import com.example.BookStore.repository.*;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
	@Autowired
	private CustomerService customerService;

	@Override
	public Customer_details createuser(Customer_details details) {

		details.setPassword(passwordEncode.encode(details.getPassword()));
		return userRepo.save(details);
	}

	@Override
	public boolean checkEmail(String email) {

		return userRepo.existsByEmail(email);
	}

	@Override
	public Customer_details getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(username);
	}

	@Override
	public Customer_details getDetails() {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUsername = authentication.getName();
		Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		return userRepo.findByEmail(loggedInUser.getEmail());
	}

	
}