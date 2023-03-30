package edu.cepuii.dao;

import edu.cepuii.models.Book;
import edu.cepuii.models.Person;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    List<Book> getAll();

    List<Book> getAllByPersonId(int personId);

    Optional<Book> getById(int bookId);

    void save(Book book);

    void update(Book book, int bookId);

    void delete(int id);

    void cleanPersonId(int id);

    void updatePersonId(int bookId, Integer personId);

    Optional<Person> getBookOwner(int id);
}
