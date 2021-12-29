package com.springboothibernate.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboothibernate.example.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
