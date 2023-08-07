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
import com.example.BookStore.service.CartService;
import com.example.BookStore.service.CustomerService;
import com.example.BookStore.service.FictionBookService;

@Controller
@RequestMapping("/user")
public class FictionBookController {
	@Autowired
	private FictionBookService fictionBookService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/fictionbooks")
	public ModelAndView humourbooks() {
		List<AllBook> list = fictionBookService.getAllBooks();
		ModelAndView m = new ModelAndView();
		m.setViewName("/user/fictionbooks");
		m.addObject("fictionbook", list);
		return m;
	}
	@RequestMapping("/myfictionList/{id}")
	public String getMyList(@PathVariable("id") int Id) {
		System.out.println(Id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		AllBook book = fictionBookService.getAllBookById(Id);
		Cart myBook = new Cart(book,loggedInUser,book.getName(),book.getAuthor(),book.getPrice());
		cartService.save(myBook);
		return "redirect:/user/fictionbooks";
	}
}