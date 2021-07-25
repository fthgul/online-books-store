package com.getir.onlinebooks.store.book.repository;

import com.getir.onlinebooks.store.book.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    Optional<Book> findBookById(Integer id);
    boolean existsByAuthorAndName(String author, String name);
}
