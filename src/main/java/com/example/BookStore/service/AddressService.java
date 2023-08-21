package com.example.BookStore.service;

import java.util.List;
import com.example.BookStore.entity.Address;

public interface AddressService {
	public List<Address> allSavedAddress(int id);
	public Address getAddressById(int id);
	public void updateAddress(int id,String fullname,String phoneno,String pincode,String area,String landmark,String city, String district,String state,String country);
	public void deleteAddressById(int id);
}