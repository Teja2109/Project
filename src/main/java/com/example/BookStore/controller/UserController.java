package com.example.BookStore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.BookStore.entity.Address;
import com.example.BookStore.entity.Cart;
import com.example.BookStore.entity.Customer_details;
import com.example.BookStore.entity.OrderDetails;
import com.example.BookStore.entity.Orders;
import com.example.BookStore.repository.AddressRepository;
import com.example.BookStore.repository.CartRepository;
import com.example.BookStore.service.AddressService;
import com.example.BookStore.service.CartService;
import com.example.BookStore.service.CustomerService;
import com.example.BookStore.service.OrdersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private AddressRepository addressRepo;

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
	public String profile(Model m) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUsername = authentication.getName();
		Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		Customer_details cd = customerService.getUserById(loggedInUser.getId());

		m.addAttribute("customer_name", cd.getFullname());
		m.addAttribute("customer_email", cd.getEmail());
		m.addAttribute("customer_phoneno", cd.getPhoneno());
		
		List<Address> ad=addressService.allSavedAddress(loggedInUser.getId());
    	m.addAttribute("savedAddresses",ad);
    	
		return "/user/profile";
	}

	@PostMapping("/profile")
	public String updateprofile(String fullname, String email, String phoneno, String password, HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUsername = authentication.getName();
		System.out.println("hii");
		Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		if (customerService.checkPassword(loggedInUser.getId(),password)) {
			customerService.updateProfile(loggedInUser.getId(), fullname, email, phoneno);
			System.out.println("hii");
		} else {
			session.setAttribute("msg", "Sorry! Wrong Password");
		}
		return "redirect:/user/profile";
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
	
	@GetMapping("/addaddress")
	public String addAddress() {
		return "user/addaddress";
	}
	
	@PostMapping("/addressadd")
	public String addressAdd(@RequestParam("fullname")String fullname, @RequestParam("phoneno") String phoneno, 
			@RequestParam("pincode")String pincode, @RequestParam("area") String area,@RequestParam("landmark") String landmark,
			@RequestParam("city")String city, @RequestParam("district") String district, @RequestParam("state") String state, 
			@RequestParam("country")String country) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUsername = authentication.getName();
		Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		Address ad = new Address(loggedInUser,fullname,phoneno,pincode,area,landmark,city,district,state,country);
		addressRepo.save(ad);
		
		return "redirect:/user/profile";
	}
	
	@GetMapping("/editaddress/{id}")
	public String editaddress(@PathVariable("id") int Id, Model m){
		Address ad = addressService.getAddressById(Id);
		m.addAttribute("addressid", Id);
		m.addAttribute("addressname", ad.getFullname());
		m.addAttribute("addressphone", ad.getPhonenumber());
		m.addAttribute("addresspin", ad.getPincode());
		m.addAttribute("addressarea", ad.getArea());
		m.addAttribute("addresslandmark", ad.getLandmark());
		m.addAttribute("addresscity", ad.getCity());
		m.addAttribute("addressdistrict", ad.getDistrict());
		m.addAttribute("addressstate", ad.getState());
		m.addAttribute("addresscountry", ad.getCountry());
		return "/user/editaddress";
	}
	
	@PostMapping("/editaddress")
	public String updateaddress(int id, String fullname, String phoneno, String pincode,
			String area, String landmark, String city, String district, String state, String country) {
		addressService.updateAddress(id, fullname, phoneno, pincode, area, landmark, city, district, state, country);
		return "redirect:/user/editaddress/"+id;
	}
	
	@RequestMapping("/deleteAddress/{id}")
	public String deleteAddress(@PathVariable("id") int Id) {
		addressService.deleteAddressById(Id);
		return "redirect:/user/profile";
	}
	
	@PostMapping("/updatepassword")
	public String updatePassword(String currentpassword, String newpassword) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUsername = authentication.getName();
		Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		if(customerService.checkPassword(loggedInUser.getId(), currentpassword)) {
			customerService.changePassword(loggedInUser.getId(), newpassword);
		}
		return "redirect:/user/profile";
	}
	
	@GetMapping("/myorders")
	public String myOrders(Model m) {
		List<Orders>  ord= ordersService.getallOrdersOfUser();
        List<List<OrderDetails>> orderdetails = new ArrayList<>();
        for(Orders i:ord){
        	List<OrderDetails> orddetails =ordersService.getallUserdetailsOfAnUser(i.getId());
        	orderdetails.add(orddetails);
        }
    	m.addAttribute("orders",ord);
    	m.addAttribute("orderdetails",orderdetails);
		return "user/myorders";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
	    SecurityContextHolder.clearContext(); // Clear security context
	    request.getSession().invalidate();   // Invalidate session
	    return "redirect:/login";            // Redirect to login page
	}

}


