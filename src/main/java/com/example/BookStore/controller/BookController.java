package com.example.BookStore.controller;

import com.example.BookStore.entity.Book;

import com.example.BookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping("/register_book")
    public String registerBook(){
        return "registerBook";
    }

    @GetMapping("available_books")
    public ModelAndView getAllBooks(){
        List<Book> list = bookService.getAllBooks();
        ModelAndView m = new ModelAndView();
        m.setViewName("bookList");
        m.addObject("book", list);
        return m;
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book){
        bookService.saveBook(book);
        return "redirect:/available_books";
    }

//    @GetMapping("/my_books")
//    public String myBooks(Model model){
//        List<MyBooks> myBooks = myBookListService.getMyAllBooks();
//        model.addAttribute("book", myBooks);
//        return "myBooks";
//    }

//    @RequestMapping("/myList/{id}")
//    public String getMyList(@PathVariable("id") int Id){
//        Book book = bookService.getBookById(Id);
//        MyBooks myBook = new MyBooks(book.getId(), book.getName(), book.getAuthor(), book.getPrice());
//        myBookListService.save(myBook);
//        return "redirect:/my_books";
//    }

    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int Id, Model model){
        Book book = bookService.getBookById(Id);
        model.addAttribute("book", book);
        return "editBook";
    }

//    @RequestMapping("/deleteBook/{id}")
//    public String deleteBook(@PathVariable("id") int Id){
//        bookService.deleteById(Id);
//        return "redirect:/available_books";
//    }
}