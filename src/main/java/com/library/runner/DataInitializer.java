package com.library.runner;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public DataInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        Author reynolds = new Author("Dr. Thomas Reynolds", "t.reynolds@university.edu", "American");
        Author mitchell = new Author("Prof. Sarah Mitchell", "s.mitchell@university.edu", "British");
        Author weber = new Author("James Weber", "j.weber@techpubs.com", "German");
        Author patel = new Author("Dr. Anita Patel", "a.patel@university.edu", "Indian");
        Author test = new Author("Test Author", "testauthor@library.com", "N/A");

        authorRepository.save(reynolds);
        authorRepository.save(mitchell);
        authorRepository.save(weber);
        authorRepository.save(patel);
        authorRepository.save(test);

        bookRepository.save(new Book("Introduction to Database Systems", "Textbook", 2020, "978-0-131-89505-5", reynolds));
        bookRepository.save(new Book("Data Structures and Algorithms", "Textbook", 2021, "978-0-133-96777-1", reynolds));
        bookRepository.save(new Book("Database Management Essentials", "Textbook", 2019, "978-0-132-57567-6", mitchell));
        bookRepository.save(new Book("SQL Fundamentals for Students", "Textbook", 2022, "978-1-491-97001-4", mitchell));
        bookRepository.save(new Book("Spring Boot in Action", "Technology", 2022, "978-1-617-29393-1", weber));
        bookRepository.save(new Book("Java: The Complete Reference", "Technology", 2021, "978-1-260-46395-1", weber));
        bookRepository.save(new Book("Clean Code", "Software Engineering", 2008, "978-0-132-35088-4", patel));
        bookRepository.save(new Book("Software Engineering Principles", "Textbook", 2023, "978-0-133-43044-1", patel));
        bookRepository.save(new Book("Test Book 1", "Reference", 2024, "978-0-000-00001-0", reynolds));
        bookRepository.save(new Book("Test Book 2", "Reference", 2024, "978-0-000-00002-7", test));
    }
}
