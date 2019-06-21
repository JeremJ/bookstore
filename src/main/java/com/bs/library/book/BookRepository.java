package com.bs.library.book;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Override
    @Cacheable(value = "allBooks")
    Page<Book> findAll(Pageable pageable);

    @Override
    @CacheEvict(value = "allBooks", allEntries = true)
    <S extends Book> S saveAndFlush(S s);

    @Override
    @CacheEvict(value = "allBooks", allEntries = true)
    void deleteById(Long aLong);

    @Override
    @CacheEvict(value = "allBooks", allEntries = true)
    <S extends Book> S save(S s);
}
