package com.example.BookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BookStore.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

	public List<Address> findAddressByUserId(int id);

	public Address findById(int id);
}