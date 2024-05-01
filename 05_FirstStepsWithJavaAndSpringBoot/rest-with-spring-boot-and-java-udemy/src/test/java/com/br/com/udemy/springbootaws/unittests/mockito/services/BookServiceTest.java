package com.br.com.udemy.springbootaws.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.com.udemy.springbootaws.exceptions.RequiredObjectIsNullException;
import com.br.com.udemy.springbootaws.model.book.Book;
import com.br.com.udemy.springbootaws.repository.book.BookRepository;
import com.br.com.udemy.springbootaws.services.book.BookService;
import com.br.com.udemy.springbootaws.unittests.mapper.mocks.MockBook;
import com.br.com.udemy.springbootaws.vo.v1.BookVO;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
	MockBook input;

	private BookService service;

	@Mock
	BookRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
		service = new BookService(repository);
	}
	
       
	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var books = service.findAll();
		
		assertNotNull(books);
		assertEquals(14, books.size());
		
		var bookOne = books.get(1);
		assertNotNull(bookOne);
		assertNotNull(bookOne.getKey());
		assertNotNull(bookOne.getLinks());
		assertTrue(bookOne.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", bookOne.getAuthor());
		assertEquals(BigDecimal.valueOf(1), bookOne.getPrice());
		assertEquals("Title Test1", bookOne.getTitle());
		assertEquals(LocalDate.now().minusDays(1).atStartOfDay(), bookOne.getLaunchDate());
		
		
		var bookFour = books.get(4);
		assertNotNull(bookFour);
		assertNotNull(bookFour.getKey());
		assertNotNull(bookFour.getLinks());
		assertTrue(bookFour.toString().contains("[</api/book/v1/4>;rel=\"self\"]"));
		assertEquals("Author Test4", bookFour.getAuthor());
		assertEquals(BigDecimal.valueOf(4), bookFour.getPrice());
		assertEquals("Title Test4", bookFour.getTitle());
		assertEquals(LocalDate.now().minusDays(4).atStartOfDay(), bookFour.getLaunchDate());
		
		var bookSeven = books.get(7);
		assertNotNull(bookSeven);
		assertNotNull(bookSeven.getKey());
		assertNotNull(bookSeven.getLinks());
		assertTrue(bookSeven.toString().contains("[</api/book/v1/7>;rel=\"self\"]"));
		assertEquals("Author Test7", bookSeven.getAuthor());
		assertEquals(BigDecimal.valueOf(7), bookSeven.getPrice());
		assertEquals("Title Test7", bookSeven.getTitle());
		assertEquals(LocalDate.now().minusDays(7).atStartOfDay(), bookSeven.getLaunchDate());
	}

	@Test
	void testFindById() {
		Book entity = input.mockEntity(1l);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(BigDecimal.valueOf(1), result.getPrice());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(LocalDate.now().minusDays(1).atStartOfDay(), result.getLaunchDate());
	}

	

	@Test
	void testCreate() {
		Book entity = input.mockEntity(1l);
		Book persisted = entity;

		BookVO vo = input.mockVO(1l);

		when(repository.save(entity)).thenReturn(persisted);
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(BigDecimal.valueOf(1), result.getPrice());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(LocalDate.now().minusDays(1).atStartOfDay(), result.getLaunchDate());
	}

	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object";
		String actualMessage = exception.getMessage();

		assertEquals(actualMessage,expectedMessage);

	}

	@Test
	void testUpdate() {
		Book entity = input.mockEntity(1l);

		Book persisted = entity;

		BookVO vo = input.mockVO(1l);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(BigDecimal.valueOf(1), result.getPrice());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(LocalDate.now().minusDays(1).atStartOfDay(), result.getLaunchDate());
	}
	
	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object";
		String actualMessage = exception.getMessage();

		assertEquals(actualMessage,expectedMessage);

	}

	@Test
	void testDelete() {
		Book entity = input.mockEntity(1l);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}

}
