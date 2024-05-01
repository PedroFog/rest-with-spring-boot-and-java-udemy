package com.br.com.udemy.springbootaws.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.com.udemy.springbootaws.model.book.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
