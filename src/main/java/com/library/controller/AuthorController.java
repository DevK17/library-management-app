package com.library.controller;

import com.library.entity.Author;
import com.library.service.AuthorService;
import com.library.service.BookService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("totalAuthors", authorService.count());
        model.addAttribute("totalBooks", bookService.count());
        return "authors/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/add";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute Author author, RedirectAttributes redirectAttributes) {
        try {
            authorService.save(author);
            redirectAttributes.addFlashAttribute("successMessage", "Author '" + author.getName() + "' added successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/authors/add";
        }
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return authorService.findById(id).map(author -> {
            model.addAttribute("author", author);
            return "authors/edit";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Author not found.");
            return "redirect:/authors";
        });
    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author, RedirectAttributes redirectAttributes) {
        try {
            author.setId(id);
            authorService.update(author);
            redirectAttributes.addFlashAttribute("successMessage", "Author updated successfully!");
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/authors/edit/" + id;
        }
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            authorService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Author deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete author: they still have books assigned.");
        }
        return "redirect:/authors";
    }

    @GetMapping("/{id}/books")
    public String viewAuthorBooks(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return authorService.findById(id).map(author -> {
            model.addAttribute("author", author);
            model.addAttribute("books", bookService.findBooksByAuthorId(id));
            return "authors/books";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Author not found.");
            return "redirect:/authors";
        });
    }
}
