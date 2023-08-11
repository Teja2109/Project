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
import com.example.BookStore.repository.CartRepository;
import com.example.BookStore.service.RomanceBookService;

import jakarta.servlet.http.HttpSession;

import com.example.BookStore.service.CartService;
import com.example.BookStore.service.CustomerService;

@Controller
@RequestMapping("/user")
public class RomanceBookController {
	@Autowired
	private RomanceBookService romanceBookService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private CartService cartService;

	@GetMapping("/romancebooks")
	public ModelAndView humourbooks() {
		List<AllBook> list = romanceBookService.getAllBooks();
		ModelAndView m = new ModelAndView();
		m.setViewName("/user/romancebooks");
		m.addObject("romancebook", list);
		return m;
	}

	@RequestMapping("/myromanceList/{id}")
	public String getMyList(@PathVariable("id") int Id, HttpSession session) {
		System.out.println(Id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUsername = authentication.getName();
		Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		AllBook book = romanceBookService.getAllBookById(Id);
		if (!(cartRepo.existsByUserIdAndAllBookId(loggedInUser.getId(), Id))) {
			Cart myBook = new Cart(book, loggedInUser);
			cartService.save(myBook);
			session.setAttribute("msg", "Book Added to Cart");
		} else {
			session.setAttribute("msg", "Book Already added!");
		}
		return "redirect:/user/romancebooks";
	}
}