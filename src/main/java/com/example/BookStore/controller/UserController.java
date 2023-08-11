package com.example.BookStore.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.BookStore.entity.Cart;
import com.example.BookStore.entity.Customer_details;
import com.example.BookStore.repository.CartRepository;
import com.example.BookStore.service.CartService;
import com.example.BookStore.service.CustomerService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartRepository cartRepo;

	@GetMapping("/availablebooks")
	public String availablebooks() {
		return "user/availablebooks";
	}

	@GetMapping("/user/mybooks")
	public String mybookss() {
		return "mybooks";
	}

	@GetMapping("/mybooks")
	public ModelAndView getAllBooks() {
		List<Cart> list = cartService.getBooksForLoggedInUser();
		ModelAndView m = new ModelAndView();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		boolean recordsPresent = cartService.isCartEmpty(loggedInUser.getId());
		m.setViewName("/user/mybooks");
		m.addObject("cart", list);
		m.addObject("recordsPresent", recordsPresent);
		return m;
	}

	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") int Id) {
		cartService.deleteById(Id);
		return "redirect:/user/mybooks";
	}

	@GetMapping("/profile")
	public ModelAndView Profile() {
		Customer_details c = customerService.getDetails();
		ModelAndView n = new ModelAndView();
		n.setViewName("/user/profile");
		n.addObject("customer_details", c);
		return n;
	}

	@RequestMapping("/incrementQuantity/{cartItemId}")
	public String incrementQuantity(@PathVariable int cartItemId) {
		Cart cartItem = cartRepo.findById(cartItemId);
		if (cartItem != null) {
			cartService.incrementQuantity(cartItem);
		}
		return "redirect:/user/mybooks"; // Redirect to cart page
	}

	@RequestMapping("/decrementQuantity/{cartItemId}")
	public String decrementQuantity(@PathVariable int cartItemId) {
		Cart cartItem = cartRepo.findById(cartItemId);
		if (cartItem != null) {
			cartService.decrementQuantity(cartItem);
		}
		return "redirect:/user/mybooks";
	}
}