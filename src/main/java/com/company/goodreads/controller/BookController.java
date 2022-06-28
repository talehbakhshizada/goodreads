package com.company.goodreads.controller;

import com.company.goodreads.dao.entity.BookReviewsEntity;
import com.company.goodreads.dao.entity.BooksEntity;
import com.company.goodreads.dao.entity.UsersEntity;
import com.company.goodreads.service.BookService;
import com.sun.istack.ByteArrayDataSource;
import liquibase.pro.packaged.id;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping  // HTTP GET sorgu dushecek bu metoda
    public String getBooks(Model model) {
        List<BooksEntity> books = bookService.getBooks();

        model.addAttribute("books", books); // booklari doldururuq model obyektine ve gonderirik thymeleafe
        return "book-list";
    }

    // sorguda bize tek id gelir deye pathvarianle istifae etdim
    //pathvariable sorgudaki idni set edir bizim id parametrine
    @GetMapping(value = "/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        BooksEntity book = bookService.getBook(id);
        model.addAttribute("book", book);
        model.addAttribute("review",new BookReviewsEntity());
        return "book";
    }

    @PostMapping(value = "/{id}/review")
    public String addReview(
            @ModelAttribute("review") BookReviewsEntity review,
            @PathVariable("id") Long bookId,
             Authentication authentication
    ){
        bookService.addReview(review, bookId, authentication.getName());
        return "redirect:/books/" + bookId;
    }

}
