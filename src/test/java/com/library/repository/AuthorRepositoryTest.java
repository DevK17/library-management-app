package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorRepository authorRepository;

    private Author savedAuthor;

    @BeforeEach
    void setUp() {
        Author author = new Author("Test Author", "test@library.com", "British");
        savedAuthor = entityManager.persistAndFlush(author);
    }

    @Test
    void findByEmail_existingEmail_returnsAuthor() {
        Optional<Author> result = authorRepository.findByEmail("test@library.com");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Author");
    }

    @Test
    void findByEmail_nonExistingEmail_returnsEmpty() {
        Optional<Author> result = authorRepository.findByEmail("nobody@library.com");
        assertThat(result).isNotPresent();
    }

    @Test
    void findAuthorsWithBooks_authorHasBooks_returnsAuthor() {
        Book book = new Book("Test Book", "Fiction", 2000, "978-0-000-00001-0", savedAuthor);
        entityManager.persistAndFlush(book);

        List<Author> result = authorRepository.findAuthorsWithBooks();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getName()).isEqualTo("Test Author");
    }

    @Test
    void findAuthorsWithBooks_authorHasNoBooks_returnsEmpty() {
        List<Author> result = authorRepository.findAuthorsWithBooks();
        assertThat(result).isEmpty();
    }

    @Test
    void findByNameContainingIgnoreCase_matchingName_returnsAuthor() {
        List<Author> result = authorRepository.findByNameContainingIgnoreCase("test");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("test@library.com");
    }

    @Test
    void save_validAuthor_persistsSuccessfully() {
        Author newAuthor = new Author("New Author", "new@library.com", "American");
        Author persisted = authorRepository.save(newAuthor);
        assertThat(persisted.getId()).isNotNull();
        assertThat(persisted.getName()).isEqualTo("New Author");
    }

    @Test
    void deleteById_existingAuthor_removesFromDatabase() {
        authorRepository.deleteById(savedAuthor.getId());
        Optional<Author> deleted = authorRepository.findById(savedAuthor.getId());
        assertThat(deleted).isNotPresent();
    }
}
