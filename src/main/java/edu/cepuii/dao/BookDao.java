package edu.cepuii.dao;

import edu.cepuii.models.Book;
import edu.cepuii.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> getAllByPersonId(int personId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new BeanPropertyRowMapper<>(Book.class), personId);
    }

    public Optional<Book> getById(int bookId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new BeanPropertyRowMapper<>(Book.class), bookId)
                .stream().findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year_of_public) VALUES (?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYearOfPublic());
    }

    public void update(Book book, int bookId) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year_of_public=? WHERE book_id=?",
                book.getTitle(), book.getAuthor(), book.getYearOfPublic(), bookId);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);
    }

    public void cleanPersonId(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE book_id=?", id);
    }

    public void updatePersonId(int bookId, Integer personId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", personId, bookId);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = person.person_id WHERE book_id=?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }
}
