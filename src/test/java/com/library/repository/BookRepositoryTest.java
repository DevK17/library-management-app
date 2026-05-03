package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    private Author savedAuthor;
    private Book savedBook;

    @BeforeEach
    void setUp() {
        Author author = new Author("Repo Author", "repoauthor@library.com", "American");
        savedAuthor = entityManager.persistAndFlush(author);

        Book book = new Book("Repo Book", "Fiction", 2001, "978-0-000-00002-0", savedAuthor);
        savedBook = entityManager.persistAndFlush(book);
    }

    @Test
    void findAllBooksWithAuthors_returnsBookWithAuthorLoaded() {
        List<Book> books = bookRepository.findAllBooksWithAuthors();
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getAuthor()).isNotNull();
        assertThat(books.get(0).getAuthor().getName()).isEqualTo("Repo Author");
    }

    @Test
    void findBooksByAuthorId_existingAuthor_returnsBooks() {
        List<Book> books = bookRepository.findBooksByAuthorId(savedAuthor.getId());
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Repo Book");
    }

    @Test
    void findBooksByAuthorId_nonExistingAuthor_returnsEmpty() {
        List<Book> books = bookRepository.findBooksByAuthorId(999L);
        assertThat(books).isEmpty();
    }

    @Test
    void existsByIsbn_existingIsbn_returnsTrue() {
        boolean exists = bookRepository.existsByIsbn("978-0-000-00002-0");
        assertThat(exists).isTrue();
    }

    @Test
    void existsByIsbn_nonExistingIsbn_returnsFalse() {
        boolean exists = bookRepository.existsByIsbn("978-0-000-99999-9");
        assertThat(exists).isFalse();
    }

    @Test
    void findByGenreIgnoreCase_matchingGenre_returnsBooks() {
        List<Book> books = bookRepository.findByGenreIgnoreCase("fiction");
        assertThat(books).hasSize(1);
    }

    @Test
    void save_validBook_persistsSuccessfully() {
        Book newBook = new Book("New Book", "Drama", 2010, "978-0-000-00003-0", savedAuthor);
        Book persisted = bookRepository.save(newBook);
        assertThat(persisted.getId()).isNotNull();
    }
}
