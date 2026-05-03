package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("Test Author", "test@library.com", "British");
        author.setId(1L);

        book = new Book("Test Book", "Fiction", 2000, "978-0-000-00001-0", author);
        book.setId(1L);
    }

    @Test
    void findAll_returnsAllBooksWithAuthors() {
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(List.of(book));
        List<Book> result = bookService.findAll();
        assertThat(result).hasSize(1);
        verify(bookRepository).findAllBooksWithAuthors();
    }

    @Test
    void findById_existingId_returnsBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> result = bookService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Test Book");
    }

    @Test
    void findById_nonExistingId_returnsEmpty() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Book> result = bookService.findById(99L);
        assertThat(result).isNotPresent();
    }

    @Test
    void findBooksByAuthorId_returnsCorrectBooks() {
        when(bookRepository.findBooksByAuthorId(1L)).thenReturn(List.of(book));
        List<Book> result = bookService.findBooksByAuthorId(1L);
        assertThat(result).hasSize(1);
    }

    @Test
    void save_uniqueIsbn_savesAndReturnsBook() {
        when(bookRepository.existsByIsbn("978-0-000-00001-0")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book saved = bookService.save(book);
        assertThat(saved.getTitle()).isEqualTo("Test Book");
        verify(bookRepository).save(book);
    }

    @Test
    void save_duplicateIsbn_throwsDataIntegrityViolationException() {
        when(bookRepository.existsByIsbn("978-0-000-00001-0")).thenReturn(true);

        assertThatThrownBy(() -> bookService.save(book))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("978-0-000-00001-0");
        verify(bookRepository, never()).save(any());
    }

    @Test
    void update_existingBook_updatesSuccessfully() {
        Book updatedBook = new Book("Updated Title", "Drama", 2005, "978-0-000-00001-0", author);
        updatedBook.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.update(updatedBook);
        assertThat(result.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void update_nonExistingBook_throwsIllegalArgumentException() {
        book.setId(99L);
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.update(book))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deleteById_callsRepositoryDelete() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.deleteById(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void count_returnsRepositoryCount() {
        when(bookRepository.count()).thenReturn(10L);
        assertThat(bookService.count()).isEqualTo(10L);
    }
}
