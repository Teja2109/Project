package com.example.BookStore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "allbook")
@Data
public class AllBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String author;
	private int price;
	private String type;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public AllBook(int id, String name, String author, int price, String type, String image) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
		this.type = type;
		this.image = image;
	}
	public AllBook() {
		super();
		// TODO Auto-generated constructor stub
	}
}