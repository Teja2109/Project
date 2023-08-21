package com.example.BookStore.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.BookStore.entity.Address;
import com.example.BookStore.entity.Cart;
import com.example.BookStore.entity.Customer_details;
import com.example.BookStore.entity.OrderDetails;
import com.example.BookStore.entity.Orders;
import com.example.BookStore.repository.AddressRepository;
import com.example.BookStore.repository.CartRepository;
import com.example.BookStore.repository.OrderDetailsRepository;
import com.example.BookStore.repository.OrdersRepository;

import jakarta.mail.internet.MimeMessage;
@Service
public class OrdersServiceImpl implements OrdersService{

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private OrdersRepository ordersRepo;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Override
	public void saveOrder(int addressId, String totalprice, String discount, String shippingcost, String grandtotal,
			String paymentmethod) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
        Address address=addressRepo.findById(addressId);
        String add =address.getFullname()+","+address.getPhonenumber()+", "+ address.getArea()+", "+address.getLandmark()+", "+address.getCity()+", "+address.getDistrict()+" district, "
        		+address.getState()+", "+address.getCountry()+" - "+address.getPincode();
        String status="Order Placed";
        LocalDateTime currentDateTime = LocalDateTime.now();
		Orders order=new Orders(loggedInUser,add,status,totalprice,discount,shippingcost,grandtotal,currentDateTime,paymentmethod);
		ordersRepo.save(order);
		List<Cart> c= cartRepo.findByUserId(loggedInUser.getId());
		for(Cart i:c) {
			
			OrderDetails d=new OrderDetails(loggedInUser,order,i.getAllBook().getName(),i.getAllBook().getAuthor(),i.getAllBook().getPrice(),
					i.getAllBook().getType(),i.getAllBook().getImage(),i.getQuantity(),i.getAllBook().getPrice()*i.getQuantity());
			orderDetailsRepo.save(d);
			cartRepo.deleteById(i.getId());
			
		}
	}

	@Override
	public void sendOrderPlacedMail(Customer_details details) {
		// TODO Auto-generated method stub
		String from = "tejaravichand2109@gmail.com";
		String to = details.getEmail();
		String subject = "Order Placed";
		String content = "Dear [[name]],<br>"
				+"Thank You for placing order with Eureka!<br>"
				+"We are excited to let you know that your order has been received and is being processed.<br>"
				+"<p></p>"
				+"<p></p>"
				+"Thank you for choosing Eureka for buying books. We appreciate your business!<br>"
				+"Best Regards,<br>"
				+"The Eureka Team";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(from,"Eureka");
			helper.setTo(to);
			helper.setSubject(subject);
			
			content = content.replace("[[name]]", details.getFullname());
			
			helper.setText(content,true);
			
			mailSender.send(message);
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	
	@Override
	public List<Orders> getallOrdersOfUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		return ordersRepo.findByUserId(loggedInUser.getId());
	}
	@Override
	public List<OrderDetails> getallUserdetailsOfAnUser(int orderid) {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        Customer_details loggedInUser = customerService.getUserByUsername(loggedInUsername);
		return orderDetailsRepo.findByOrderIdAndUserId(orderid,loggedInUser.getId());
	}

	@Override
	public void cancelOrder(int id) {
		// TODO Auto-generated method stub\
		Orders s1 = ordersRepo.findById(id);
		s1.setStatus("Cancelled");
		s1.setOrderCancelDate(LocalDateTime.now());
		ordersRepo.save(s1);		
	}
	
	

}