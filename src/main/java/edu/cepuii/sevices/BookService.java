package edu.cepuii.sevices;

import edu.cepuii.models.Book;
import edu.cepuii.models.Person;
import edu.cepuii.repositories.BookRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    public Book getById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void create(Book book) {
        bookRepository.save(book);
    }

    public void update(int id, Book book) {
        book.setBookId(id);
        bookRepository.save(book);
    }

    public void delete(int id) {
        bookRepository.deleteById(id);
    }


    public void updatePersonId(int id, Person person) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setOwner(person);
        book.setInOwe(new Date());
        bookRepository.save(book);
    }

    public void cleanPersonId(int id) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setOwner(null);
        book.setInOwe(null);
        book.setDelay(false);
        bookRepository.save(book);
    }

    public List<Book> findByTitle(String title) {
        List<Book> booksByTitleLike = bookRepository.findBooksByTitleLike("%" + title + "%");
        booksByTitleLike.forEach(System.out::println);
        return booksByTitleLike;
    }
}
