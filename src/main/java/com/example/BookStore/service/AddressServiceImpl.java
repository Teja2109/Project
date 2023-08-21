package com.example.BookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.BookStore.entity.Address;
import com.example.BookStore.repository.AddressRepository;
@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	public AddressRepository addressRepo;
	
	@Override
	public List<Address> allSavedAddress( int id) {
		// TODO Auto-generated method stub
        
		return addressRepo.findAddressByUserId(id);
	}

	@Override
	public Address getAddressById(int id) {
		// TODO Auto-generated method stub
		return addressRepo.findById(id);
	}

	@Override
	public void updateAddress(int id, String fullname, String phoneno, String pincode, String area, String landmark,
			String city, String district, String state, String country) {
		// TODO Auto-generated method stub
		Address ad = addressRepo.findById(id);
		ad.setFullname(fullname);
		ad.setPhonenumber(phoneno);
		ad.setPincode(pincode);
		ad.setArea(area);
		ad.setLandmark(landmark);
		ad.setCity(city);
		ad.setDistrict(district);
		ad.setState(state);
		ad.setCountry(country);
		addressRepo.save(ad);
		
	}

	@Override
	public void deleteAddressById(int id) {
		// TODO Auto-generated method stub\
		addressRepo.deleteById(id);
		
	}

}