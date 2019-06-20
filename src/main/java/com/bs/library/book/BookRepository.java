package com.bs.library.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {

}
