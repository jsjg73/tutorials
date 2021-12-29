package com.springboothibernate.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboothibernate.example.models.Book;
import com.springboothibernate.example.services.BookService;

@SpringBootTest
public class BookServiceUnitTest {
	@Autowired
	private BookService bookService;
	
	@Test
	public void whenAppStarts_thenHibernateCreatesInitialRecords() {
		List<Book> books = bookService.list();
		
		assertEquals(books.size(), 3);
	}
}
