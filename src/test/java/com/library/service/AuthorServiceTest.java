package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
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
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Test Author", "test@library.com", "British");
        author.setId(1L);
    }

    @Test
    void findAll_returnsAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author));
        List<Author> result = authorService.findAll();
        assertThat(result).hasSize(1);
        verify(authorRepository).findAll();
    }

    @Test
    void findById_existingId_returnsAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Optional<Author> result = authorService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Author");
    }

    @Test
    void findById_nonExistingId_returnsEmpty() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Author> result = authorService.findById(99L);
        assertThat(result).isNotPresent();
    }

    @Test
    void save_uniqueEmail_savesAndReturnsAuthor() {
        when(authorRepository.findByEmail("test@library.com")).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author saved = authorService.save(author);
        assertThat(saved.getName()).isEqualTo("Test Author");
        verify(authorRepository).save(author);
    }

    @Test
    void save_duplicateEmail_throwsDataIntegrityViolationException() {
        when(authorRepository.findByEmail("test@library.com")).thenReturn(Optional.of(author));

        assertThatThrownBy(() -> authorService.save(author))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("test@library.com");
        verify(authorRepository, never()).save(any());
    }

    @Test
    void update_existingAuthor_updatesSuccessfully() {
        Author updated = new Author("Updated Name", "test@library.com", "American");
        updated.setId(1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.findByEmail("test@library.com")).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(updated);

        Author result = authorService.update(updated);
        assertThat(result.getName()).isEqualTo("Updated Name");
    }

    @Test
    void update_nonExistingAuthor_throwsIllegalArgumentException() {
        author.setId(99L);
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.update(author))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deleteById_callsRepositoryDelete() {
        doNothing().when(authorRepository).deleteById(1L);
        authorService.deleteById(1L);
        verify(authorRepository).deleteById(1L);
    }

    @Test
    void count_returnsRepositoryCount() {
        when(authorRepository.count()).thenReturn(5L);
        assertThat(authorService.count()).isEqualTo(5L);
    }
}
