package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public List<Author> findAuthorsWithBooks() {
        return authorRepository.findAuthorsWithBooks();
    }

    public Author save(Author author) {
        if (authorRepository.findByEmail(author.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("An author with email '" + author.getEmail() + "' already exists.");
        }
        return authorRepository.save(author);
    }

    public Author update(Author author) {
        Author existing = authorRepository.findById(author.getId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + author.getId()));
        Optional<Author> byEmail = authorRepository.findByEmail(author.getEmail());
        if (byEmail.isPresent() && !byEmail.get().getId().equals(author.getId())) {
            throw new DataIntegrityViolationException("Email '" + author.getEmail() + "' is already in use.");
        }
        existing.setName(author.getName());
        existing.setEmail(author.getEmail());
        existing.setNationality(author.getNationality());
        return authorRepository.save(existing);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public long count() {
        return authorRepository.count();
    }
}
