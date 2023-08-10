package com.example.BookStore.service;

import java.util.List;

import com.example.BookStore.entity.Cart;

public interface CartService 
{
	public List<Cart> getBooksForLoggedInUser();
	public void save(Cart book);
    void deleteById(int Id);
    public boolean isCartEmpty(int userid);
    public Cart incrementQuantity(Cart cartItem);
    public Cart decrementQuantity(Cart cartItem);
}