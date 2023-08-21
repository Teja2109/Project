package com.example.BookStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.BookStore.entity.*;
import com.example.BookStore.repository.*;

import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public Customer_details createuser(Customer_details details) {

		details.setPassword(passwordEncode.encode(details.getPassword()));
		details.setEnabled(false);
		RandomString rn = new RandomString();
		details.setVerificationCode(rn.make(64));
		
		Customer_details us = userRepo.save(details);
		
		sendVerificationMail(details);
		
		return us;
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

	@Override
	public void sendVerificationMail(Customer_details details) {
		// TODO Auto-generated method stub
		String from = "tejaravichand2109@gmail.com";
		String to = details.getEmail();
		String subject = "Account Verification";
		String content = "Dear [[name]],<br>"
						+"Please click the link below to verify your registration:<br>"
						+"<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
						+"Thank you,<br>"
						+"Eureka.";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(from,"Eureka");
			helper.setTo(to);
			helper.setSubject(subject);
			
			content = content.replace("[[name]]", details.getFullname());
			
			String siteUrl = "http://localhost:8080"+"/verify?code="+details.getVerificationCode();
			
			content = content.replace("[[URL]]", siteUrl);
			
			helper.setText(content,true);
			
			mailSender.send(message);
		}
		catch(Exception e){
			e.printStackTrace();
			
		} 
		
	}

	@Override
	public boolean verifyAccount(String code) {
		// TODO Auto-generated method stub
		Customer_details details = userRepo.findByVerificationCode(code);
		if(details!=null) {
			details.setEnabled(true);
			details.setVerificationCode(null);
			userRepo.save(details);
			return  true;
		}
		return false;
	}

	@Override
	public Customer_details getUserById(int id) {
		
		// TODO Auto-generated method stub
		return userRepo.findById(id);
	} 
	
	@Override
	public boolean checkPassword(int id,String ps) {
		// TODO Auto-generated method stub
		Customer_details d=userRepo.findById(id);
		if(passwordEncode.matches(ps,d.getPassword())) {
			return true;
		}
		return false;
	}
	@Override
	public void updateProfile(int id,String name, String email, String phoneno) {
		// TODO Auto-generated method stub
		Customer_details details =userRepo.findById(id);
		details.setFullname(name);
		details.setEmail(email);
		details.setPhoneno(phoneno);
		userRepo.save(details);
		
	}

	@Override
	public void sendChangePasswordMail(String email) {
		// TODO Auto-generated method stub
		String from = "tejaravichand2109@gmail.com";
		String to = email;
		String subject = "Password Reset";
		String content = "Dear [[name]],<br>"
				+"Please click the link below to reset your Password:<br>"
				+"<h3><a href=\"[[URL]]\" target=\"_self\">RESET</a></h3>"
				+"Thank you,<br>"
				+"Eureka.";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(from,"Eureka");
			helper.setTo(to);
			helper.setSubject(subject);
			
			
			Customer_details details = customerService.getUserByUsername(email);
			RandomString rn = new RandomString();
			details.setVerificationCode(rn.make(64));
			
			userRepo.save(details);
			
			content = content.replace("[[name]]", details.getFullname());
			
			String siteUrl = "http://localhost:8080"+"/reset/"+details.getId()+"?code="+details.getVerificationCode();
			
			content = content.replace("[[URL]]", siteUrl);
			
			helper.setText(content,true);
			
			mailSender.send(message);
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
	}

	@Override
	public void changePassword(int id, String password) {
		// TODO Auto-generated method stub
		Customer_details details = userRepo.findById(id);
		
		details.setPassword(passwordEncode.encode(password));
		
		userRepo.save(details);		
	}


	
}