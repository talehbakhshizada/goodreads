package com.company.goodreads.controller;

import com.company.goodreads.dao.entity.BooksEntity;
import com.company.goodreads.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/create")
    public String getCreateBookPage(Model model) {
        model.addAttribute("book", new BooksEntity());

        return "create-book";
    }

    @GetMapping("/books/{id}/update")
    public String getUpdateBookPage(@PathVariable("id") Long id, Model model) {
        BooksEntity book = bookService.getBook(id);
        model.addAttribute("book", book);

        return "update-book";
    }
    @PostMapping("/books/create")
    public String createBook(@ModelAttribute BooksEntity book) {
        bookService.createBook(book);

        return "redirect:/books";
    }

    @PostMapping("/books/{id}/update")
    public String updateBook(@ModelAttribute BooksEntity book,
                             @PathVariable("id") Long id) {
        bookService.updateBook(id, book);

        return "redirect:/books";
    }


    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);

        return "redirect:/books";
    }
}
