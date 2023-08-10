package com.example.BookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.BookStore.service.AddNewBook;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AddNewBook addnewbook;
	@GetMapping("/addNewBook")
	public String addNewBook()
	{
		return "admin/addnewbook";
	}
	
	 @PostMapping("/add")
	    public String add(@RequestParam("image") MultipartFile image,
	    		@RequestParam("name") String name,
	    		@RequestParam("author") String author,
	    		@RequestParam("price") String price,
	    		@RequestParam("type") String type)
	    {
	    	addnewbook.saveProductToDB(image, name,author, price,type);
	    	return "redirect:/admin/addnewbook";
	    }
	
}