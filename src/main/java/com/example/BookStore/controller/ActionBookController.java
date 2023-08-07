package com.example.BookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.BookStore.entity.AllBook;
import com.example.BookStore.entity.Cart;
import com.example.BookStore.entity.Customer_details;
import com.example.BookStore.service.ActionBookService;
import com.example.BookStore.service.CartService;
import com.example.BookStore.service.CustomerService;

@Controller
@RequestMapping("/user")
public class ActionBookController {
	@Autowired
	private ActionBookService actionBookService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/actionbooks")
	public ModelAndView humourbooks() {
		List<AllBook> list = actionBookService.getAllBooks();
		ModelAndView m = new ModelAndView();
		m.setViewName("/user/actionbooks");
		m.addObject("actionbook", list);
		return m;
	}
	@RequestMapping("/myactionList/{id}")
	public String getMyList(@PathVariable("id") int Id) {
		System.out.println(Id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		AllBook book = actionBookService.getAllBookById(Id);
		Cart myBook = new Cart(book,loggedInUser,book.getName(),book.getAuthor(),book.getPrice());
		cartService.save(myBook);
		return "redirect:/user/actionbooks";
	}
}