package com.example.BookStore.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.BookStore.entity.Cart;
import com.example.BookStore.entity.Customer_details;
import com.example.BookStore.service.CartService;
import com.example.BookStore.service.CustomerService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CartService cartService;
	
	@Autowired 
	private CustomerService customerService;
	
	@GetMapping("/availablebooks")
	public String availablebooks() {
		return "user/availablebooks";
	}

	@GetMapping("/user/mybooks")
	public String mybookss() {
		return "mybooks";
	}
	@GetMapping("/mybooks")
    public ModelAndView getAllBooks(){
        List<Cart> list = cartService.getBooksForLoggedInUser();
        ModelAndView m = new ModelAndView();
        m.setViewName("/user/mybooks");
        m.addObject("cart", list);
        return m;
	}
	@RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int Id){
        cartService.deleteById(Id);
        return "redirect:/user/mybooks";
    }
	@GetMapping("/profile")
	public ModelAndView Profile() {
		Customer_details c = customerService.getDetails();
		ModelAndView n = new ModelAndView();
		n.setViewName("/user/profile");
		n.addObject("customer_details",c);
		return n;
	}
}