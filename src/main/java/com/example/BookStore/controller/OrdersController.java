package com.example.BookStore.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.BookStore.entity.Address;
import com.example.BookStore.entity.Cart;
import com.example.BookStore.entity.Customer_details;
import com.example.BookStore.repository.AddressRepository;
import com.example.BookStore.service.AddressService;
import com.example.BookStore.service.CartService;
import com.example.BookStore.service.CustomerService;
import com.example.BookStore.service.OrdersService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class OrdersController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressRepository addressRepo;

	@RequestMapping("/orderplaced")
	public String orderplaced()
	{
		
		return "/user/demo";
	}
	
	@PostMapping("/orderplaced")
	public String order(@RequestParam(name="selectedAddress",required = false) Integer id,
			@RequestParam("totalprice") String totalprice,
			String discount,
			@RequestParam("shipcost")String shippingcost,String grandtotal,HttpSession session ,String paymentMethod)
	{
		if(id==null)
		{
			session.setAttribute("msg","Please Add Appropriate Address");
			return "redirect:/user/orders";
		}
		else {
			int a=id.intValue();
			ordersService.saveOrder(a,totalprice,discount, shippingcost, grandtotal,paymentMethod);
			ordersService.sendOrderPlacedMail(customerService.getDetails());
	        return "/user/demo";
		}
	}
	
	@RequestMapping("/orders")
    public String orders(Model m)
    {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
    	List<Cart> list = cartService.getBooksForLoggedInUser();
    	m.addAttribute("item",list);
    	List<Address> ad=addressService.allSavedAddress(loggedInUser.getId());
    	m.addAttribute("savedAddresses",ad);
    	return "/user/orders";
    }
	
	@GetMapping("/addAddress")
	public String addAddress() {
		return "user/addOrderAddress";
	}
	
	@PostMapping("/addressAdd")
	public String addressAdd(@RequestParam("fullname")String fullname, @RequestParam("phoneno") String phoneno, 
			@RequestParam("pincode")String pincode, @RequestParam("area") String area,@RequestParam("landmark") String landmark,
			@RequestParam("city")String city, @RequestParam("district") String district, @RequestParam("state") String state, 
			@RequestParam("country")String country) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUsername = authentication.getName();
		Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		Address ad = new Address(loggedInUser,fullname,phoneno,pincode,area,landmark,city,district,state,country);
		addressRepo.save(ad);
		
		return "redirect:/user/orders";
	}
	
	@RequestMapping("/cancelorder/{id}")
	public String cancelOrder(@PathVariable("id") int Id) {
		ordersService.cancelOrder(Id);
		return "redirect:/user/myorders";
	}
}
