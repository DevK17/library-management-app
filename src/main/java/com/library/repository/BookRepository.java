package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b INNER JOIN b.author a ORDER BY a.name, b.title")
    List<Book> findAllBooksWithAuthors();

    @Query("SELECT b FROM Book b INNER JOIN b.author a WHERE a.id = :authorId")
    List<Book> findBooksByAuthorId(@Param("authorId") Long authorId);

    List<Book> findByGenreIgnoreCase(String genre);

    boolean existsByIsbn(String isbn);
}
