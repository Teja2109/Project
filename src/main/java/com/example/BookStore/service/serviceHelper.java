package com.example.BookStore.service;

import org.springframework.stereotype.Component;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpSession;

@Component
public class serviceHelper {
	public void removeMessageFromSession() {
		try { 
			 HttpSession session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			 session.removeAttribute("msg"); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void removeMessageFromSession2() {
		try { 
			 HttpSession session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			 session.removeAttribute("msg1"); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}