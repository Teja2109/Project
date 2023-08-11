package com.example.BookStore.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.example.BookStore.entity.AllBook;
import com.example.BookStore.repository.AllBookRepository;

@Service
public class AddNewBook {
	@Autowired
	private AllBookRepository bookRepo;
	
	public void  saveProductToDB(MultipartFile image,String name,String author,int price,String type)
	{
		AllBook p = new AllBook();
		String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		if(fileName.contains(".."))
		{
			System.out.println("not a a valid file");
		}
		try {
			p.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        p.setName(name);
        p.setPrice(price);
        p.setAuthor(author);
        p.setType(type);
        
        bookRepo.save(p);
	}
}