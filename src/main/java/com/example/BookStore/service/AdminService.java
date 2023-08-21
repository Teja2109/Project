package com.example.BookStore.service;

import java.util.List;


import com.example.BookStore.entity.AllBook;
import com.example.BookStore.entity.Customer_details;
import com.example.BookStore.entity.OrderDetails;
import com.example.BookStore.entity.Orders;

public interface AdminService {

	public List<AllBook> getAllBooks();
	public AllBook getBookById(int id);
	public void updateBook(int id,String name,String author,int  price,String booktype,String image);
	public void deleteById(int id);
	public List<Customer_details> getAllCustomers();
	public List<Orders> getAllOrdes();
	public List<OrderDetails> getallOrderdetailsById(int orderid);
}