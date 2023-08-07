package com.example.BookStore.controller;

import com.example.BookStore.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyBookController {

    @Autowired
    private MyBookListService myBookListService;
    @RequestMapping("/deleteFromMyBooks/{id}")
    public String deleteFromMyBooks(@PathVariable("id") int Id){
        myBookListService.deleteById(Id);
        return "redirect:/my_books";
    }
}