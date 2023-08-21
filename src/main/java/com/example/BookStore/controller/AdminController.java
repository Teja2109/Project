package com.example.BookStore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.BookStore.entity.AllBook;
import com.example.BookStore.entity.Customer_details;
import com.example.BookStore.entity.OrderDetails;
import com.example.BookStore.entity.Orders;
import com.example.BookStore.service.AddNewBook;
import com.example.BookStore.service.AdminService;
import com.example.BookStore.service.CustomerService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AddNewBook addnewbook;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CustomerService customerService;

	@GetMapping("/addnewbook")
	public String addNewBook() {
		return "admin/addnewbook";
	}

	@PostMapping("/add")
	public String add(@RequestParam("image") MultipartFile image, @RequestParam("name") String name,
			@RequestParam("author") String author, @RequestParam("price") int price,
			@RequestParam("type") String type) {
		addnewbook.saveProductToDB(image, name, author, price, type);
		return "admin/addnewbook";
	}

	@GetMapping("/availablebooks")
	public String home(Model m) {
		List<AllBook> books = adminService.getAllBooks();
		m.addAttribute("allbooks", books);
		m.addAttribute("count", books.size());
		return "admin/adminavailablebooks";
	}

	@GetMapping("/editbook/{id}")
	public String editbook(@PathVariable("id") int Id, Model m) {
		AllBook b = adminService.getBookById(Id);
		m.addAttribute("bookid", Id);
		m.addAttribute("bookname", b.getName());
		m.addAttribute("bookauthor", b.getAuthor());
		m.addAttribute("bookprice", b.getPrice());
		m.addAttribute("booktype", b.getType());
		m.addAttribute("bookimage", b.getImage());
		return "/admin/editbook";
	}

	@PostMapping("/editbook")
	public String updatebook(int id, String name, String author, int price, String booktype,
			@RequestParam("image") MultipartFile image) throws IOException {
		String img = Base64.getEncoder().encodeToString(image.getBytes());
		adminService.updateBook(id, name, author, price, booktype, img);

		return "redirect:/admin/editbook/" + id;
	}

	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") int Id) {
		adminService.deleteById(Id);
		return "redirect:/admin/availablebooks";
	}
	
	@GetMapping("/userorders")
	public String userOrders(Model m) {
		List<Customer_details> customers = adminService.getAllCustomers();
		List<Orders>  ord= adminService.getAllOrdes();
		List<List<OrderDetails>> orderdetails = new ArrayList<>();
		for(Orders i : ord) {
			List<OrderDetails> orddetails = adminService.getallOrderdetailsById(i.getId());
			orderdetails.add(orddetails);
		}
		m.addAttribute("orders", ord);       
		m.addAttribute("orderdetails", orderdetails);
		m.addAttribute("customers", customers);
		return "admin/userorders";
	}
	
	@GetMapping("/customers")
	public String customerDetails(Model m) {
		List<Customer_details> customers = adminService.getAllCustomers();
		m.addAttribute("customers", customers);
		return "admin/customers";
	}
}