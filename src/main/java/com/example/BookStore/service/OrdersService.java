package com.example.BookStore.service;

import java.util.List;

import com.example.BookStore.entity.Customer_details;
import com.example.BookStore.entity.OrderDetails;
import com.example.BookStore.entity.Orders;

public interface OrdersService {

	public void saveOrder(int addressId, String totalprice, String discount, String shippingcost, String grandtotal,
			String paymentmethod);

	public void sendOrderPlacedMail(Customer_details details);
	
	public List<Orders> getallOrdersOfUser();
	public List<OrderDetails> getallUserdetailsOfAnUser(int orderid);
	public void cancelOrder(int id);
}