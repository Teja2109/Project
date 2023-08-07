package com.example.BookStore.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.BookStore.service.*;
import com.example.BookStore.entity.*;
import com.example.BookStore.repository.CustomerRepository;
@Controller
public class HomeController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private BCryptPasswordEncoder pswEncoder;
	
	@GetMapping("/")
    public String home(){
        return "home";
    }
	
	@GetMapping("/signin")
    public String signin(){
        return "signin";
	}
	@GetMapping("/register")
    public String register(){
        return "register";
	}
	
	@PostMapping("/createuser")
	public String createuser(@ModelAttribute Customer_details details,HttpSession session) {
		boolean f = customerService.checkEmail(details.getEmail());
		
		if (f) {
			session.setAttribute("msg","Email Id alreday exists");
			return "redirect:/register";
		}
		else {
		Customer_details cs=customerService.createuser(details);
		if(cs!=null) {
			System.out.println("Success");
			session.setAttribute("msg1","Successfully Registered!!");
//			return "redirect:/customer_sign";
		}
		}
		return "redirect:/signin";
		
	}

	@GetMapping("/forgotPassword")
	public String loadForgotPassword() {
		return "forgotPassword";
	}
	
	@GetMapping("/resetPassword/{id}")
	public String loadresetPassword(@PathVariable int id, Model m) {
		m.addAttribute("id",id);
		return "resetPassword";
	}
	
	@PostMapping("/forgotPsw")
	public String forgotPassword(@RequestParam String email, @RequestParam String phoneno, HttpSession session) {
		Customer_details user = customerRepo.findByEmailAndPhoneno(email, phoneno);
		if(user!=null) {
			return "redirect:/resetPassword/" + user.getId();
		}
		else {
			session.setAttribute("msg1", "invalid email or mobile number");
			return "forgotPassword";
		}
				
	}
	
	@PostMapping("/changePassword")
	public String resetPassword(@RequestParam String psw, @RequestParam Integer id, HttpSession session ) {
		Customer_details user = customerRepo.findById(id).get();
		String epsw = pswEncoder.encode(psw);
		user.setPassword(epsw);
		Customer_details updatedUser = customerRepo.save(user);
		if(updatedUser!=null) {
			session.setAttribute("msg1", "Password changed successfully");
		}
		return "redirect:/forgotPassword";
	}
	
}
