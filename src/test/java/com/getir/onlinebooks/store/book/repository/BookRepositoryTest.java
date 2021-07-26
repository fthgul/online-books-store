package com.getir.onlinebooks.store.book.repository;

import com.getir.onlinebooks.store.book.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest extends BaseRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void whenFindBookByIdTest() {
        Book book = buildBookEntity();
        final Book persistedBook = entityManager.persist(book);

        final Book fetchedBook = bookRepository.findBookById(persistedBook.getId()).get();
        assertThat(book.getName()).isEqualTo(fetchedBook.getName());
        assertThat(book.getAuthor()).isEqualTo(fetchedBook.getAuthor());
        assertThat(book.getInventory().getQuantity()).isEqualTo(fetchedBook.getInventory().getQuantity());
    }

    @Test
    void WhenNotFindBookByIdTest() {
        final Optional<Book> optionalBook = bookRepository.findBookById(2);
        assertThat(optionalBook.isPresent()).isFalse();
    }

    @Test
    void WhenExistsByAuthorAndNameTest() {
        Book book = buildBookEntity();
        entityManager.persist(book);

        final boolean isExist = bookRepository.existsByAuthorAndName(book.getAuthor(), book.getName());
        assertThat(isExist).isTrue();
    }

    @Test
    void WhenNotExistsByAuthorAndNameTest() {
        final boolean isExist = bookRepository.existsByAuthorAndName("testWriter", "testName");
        assertThat(isExist).isFalse();
    }
}
