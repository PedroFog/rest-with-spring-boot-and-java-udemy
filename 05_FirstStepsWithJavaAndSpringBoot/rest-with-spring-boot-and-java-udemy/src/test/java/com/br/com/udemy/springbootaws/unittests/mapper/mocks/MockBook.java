package com.br.com.udemy.springbootaws.unittests.mapper.mocks;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.br.com.udemy.springbootaws.model.book.Book;
import com.br.com.udemy.springbootaws.vo.v1.BookVO;

public class MockBook {

	 public Book mockEntity() {
	        return mockEntity(0l);
	    }
	    
	    public BookVO mockVO() {
	        return mockVO(0l);
	    }
	    
	    public List<Book> mockEntityList() {
	        List<Book> books = new ArrayList<Book>();
	        for (long i = 0; i < 14; i++) {
	        	books.add(mockEntity(i));
	        }
	        return books;
	    }

	    public List<BookVO> mockVOList() {
	        List<BookVO> books = new ArrayList<>();
	        for (long i = 0; i < 14; i++) {
	        	books.add(mockVO(i));
	        }
	        return books;
	    }
	    
	    public Book mockEntity(Long number) {
	        Book book = new Book();
	        book.setAuthor("Author Test" + number);
	        book.setId(number);
	        book.setLaunchDate(LocalDate.now().minusDays(number).atStartOfDay());
	        book.setPrice(BigDecimal.valueOf(number));
	        book.setTitle("Title Test" + number);
	        return book;
	    }

	    public BookVO mockVO(Long number) {
	        BookVO book = new BookVO();
	        book.setAuthor("Author Test" + number);
	        book.setKey(number);
	        book.setLaunchDate(LocalDate.now().minusDays(number).atStartOfDay());
	        book.setPrice(BigDecimal.valueOf(number));
	        book.setTitle("Title Test" + number);
	        return book;
	    }
}
