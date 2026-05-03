package com.library.controller;

import com.library.service.AuthorService;
import com.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AuthorService authorService;
    private final BookService bookService;

    public HomeController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalAuthors", authorService.count());
        model.addAttribute("totalBooks", bookService.count());
        model.addAttribute("recentBooks", bookService.findAll());
        return "home";
    }
}
