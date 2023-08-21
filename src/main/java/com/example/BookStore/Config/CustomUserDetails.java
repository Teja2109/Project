package com.example.BookStore.Config;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.BookStore.entity.Customer_details;

public class CustomUserDetails implements UserDetails{

	private Customer_details d;
	
	public CustomUserDetails(Customer_details d) {
		super();
		this.d = d;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		HashSet<SimpleGrantedAuthority> set=new HashSet<SimpleGrantedAuthority>();
		set.add(new SimpleGrantedAuthority(d.getRole()));
		return set;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return d.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return d.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return d.getEnabled();
	}

}