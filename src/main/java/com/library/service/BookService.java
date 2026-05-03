package com.library.service;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAllBooksWithAuthors();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findBooksByAuthorId(Long authorId) {
        return bookRepository.findBooksByAuthorId(authorId);
    }

    public Book save(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DataIntegrityViolationException("A book with ISBN '" + book.getIsbn() + "' already exists.");
        }
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        Book existing = bookRepository.findById(book.getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + book.getId()));
        existing.setTitle(book.getTitle());
        existing.setGenre(book.getGenre());
        existing.setPublishedYear(book.getPublishedYear());
        existing.setIsbn(book.getIsbn());
        existing.setAuthor(book.getAuthor());
        return bookRepository.save(existing);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public long count() {
        return bookRepository.count();
    }
}
