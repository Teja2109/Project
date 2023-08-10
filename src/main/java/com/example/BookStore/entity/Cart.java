package com.example.BookStore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private AllBook allBook;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer_details user;

//    private String name;
//	private String author;
//	private String price;
//	@Lob
//	@Column(columnDefinition = "MEDIUMBLOB")
//	private String image;
	
	private int quantity=1;
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getAuthor() {
//		return author;
//	}
//
//	public void setAuthor(String author) {
//		this.author = author;
//	}
//
//	public String getPrice() {
//		return price;
//	}
//
//	public void setPrice(String price) {
//		this.price = price;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AllBook getAllBook() {
		return allBook;
	}

	public void setAllBook(AllBook allBook) {
		this.allBook = allBook;
	}

	public Customer_details getUser() {
		return user;
	}

	public void setUser(Customer_details user) {
		this.user = user;
	}

//	public String getImage() {
//		return image;
//	}
//
//	public void setImage(String image) {
//		this.image = image;
//	}
	public Cart( AllBook allBook, Customer_details user) {
		super();
		this.allBook = allBook;
		this.user = user;
//		this.name = name;
//		this.author = author;
//		this.price = price;
//		this.image = image;
	}
	
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Cart(int quantity) {
		super();
		this.quantity = quantity;
	}

}