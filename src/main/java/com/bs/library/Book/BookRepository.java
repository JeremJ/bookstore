package com.bs.library.Book;

import org.springframework.data.jpa.repository.JpaRepository;

interface BookRepository extends JpaRepository<Book, Long> {
}
