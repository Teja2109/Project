package com.example.BookStore.entity;

import jakarta.persistence.*;

import lombok.Data;


@Entity
@Table(name = "orderdetails")
@Data
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer_details user;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders order;
	
//	@ManyToOne
//	@JoinColumn(name = "book_id")
//	private AllBook allBook;
	
	private String bookName;
	private String bookAuthor;
	private int bookPrice;
	private String bookType;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String bookImage;
	private int quantity;
	private int subtotal;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Customer_details getUser() {
		return user;
	}
	public void setUser(Customer_details user) {
		this.user = user;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public int getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}
	public String getBookType() {
		return bookType;
	}
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	public String getBookImage() {
		return bookImage;
	}
	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
	public OrderDetails(Customer_details user, Orders order, String bookName, String bookAuthor, int bookPrice,
			String bookType, String bookImage, int quantity, int subtotal) {
		super();
		this.user = user;
		this.order = order;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPrice = bookPrice;
		this.bookType = bookType;
		this.bookImage = bookImage;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}
	public OrderDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}