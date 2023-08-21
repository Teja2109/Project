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
	
	private int quantity=1;

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

	public Cart( AllBook allBook, Customer_details user) {
		super();
		this.allBook = allBook;
		this.user = user;
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