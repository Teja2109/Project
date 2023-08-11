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
import com.example.BookStore.service.CartService;
import com.example.BookStore.service.CustomerService;
import com.example.BookStore.service.HumourBookService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class HumourBookController {
	@Autowired
	private HumourBookService humourBookService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartRepository cartRepo;

	@GetMapping("/humourbooks")
	public ModelAndView humourbooks() {
		List<AllBook> list = humourBookService.getAllBooks();
		ModelAndView m = new ModelAndView();
		m.setViewName("/user/humourbooks");
		m.addObject("humourbook", list);
		return m;
	}

	@RequestMapping("/myhumourList/{id}")
	public String getMyList(@PathVariable("id") int Id, HttpSession session) {
		System.out.println(Id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUsername = authentication.getName();
		Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		AllBook book = humourBookService.getAllBookById(Id);
		if (!(cartRepo.existsByUserIdAndAllBookId(loggedInUser.getId(), Id))) {
			Cart myBook = new Cart(book, loggedInUser);
			cartService.save(myBook);
			session.setAttribute("msg", "Book Added to Cart");
		} else {
			session.setAttribute("msg", "Book Already added!");
		}
		return "redirect:/user/humourbooks";
	}
}