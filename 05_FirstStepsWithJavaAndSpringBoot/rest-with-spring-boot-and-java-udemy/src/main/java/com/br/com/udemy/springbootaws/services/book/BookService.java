package com.br.com.udemy.springbootaws.services.book;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.br.com.udemy.springbootaws.controller.book.BookController;
import com.br.com.udemy.springbootaws.exceptions.RequiredObjectIsNullException;
import com.br.com.udemy.springbootaws.exceptions.ResourceNotFoundException;
import com.br.com.udemy.springbootaws.mapper.DozerMapper;
import com.br.com.udemy.springbootaws.model.book.Book;
import com.br.com.udemy.springbootaws.repository.book.BookRepository;
import com.br.com.udemy.springbootaws.vo.v1.BookVO;

@Service
public class BookService {

	private Logger logger = Logger.getLogger(BookService.class.getName());

	private final BookRepository repository;

	public BookService(BookRepository repository) {
		this.repository = repository;
	}

	public List<BookVO> findAll() {
		logger.info("Finding all books");
		var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		books.stream().forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
		return books;
	}

	public BookVO findById(Long id) {
		logger.info("Finding one book by id");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found to this ID"));
		var bookVO = DozerMapper.parseObject(entity, BookVO.class);
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookVO;
	}

	public BookVO create(BookVO bookVO) {
		logger.info("Creating a new book");
		if (bookVO == null) {
			throw new RequiredObjectIsNullException();
		}
		var entity = DozerMapper.parseObject(bookVO, Book.class);
		bookVO = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	public BookVO update(BookVO book) {
		if (book == null)
			throw new RequiredObjectIsNullException();
		
		logger.info("Updating a book");
		var entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		BeanUtils.copyProperties(book, entity);
		entity.setId(book.getKey());
		
		var bookVO = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	public void delete(Long id) {
		logger.info("Removing one bookO");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.delete(entity);

	}

}
