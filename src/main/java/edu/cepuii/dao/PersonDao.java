package edu.cepuii.dao;

import edu.cepuii.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getById(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public Optional<Person> getByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email=?", new BeanPropertyRowMapper<>(Person.class), new Object[]{email})
                .stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (name, surname, age, email, address) VALUES (?,?,?,?,?)",
                person.getName(), person.getSurname(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, surname=?, age=?, email=?, address=? WHERE id=?",
                person.getName(), person.getSurname(), person.getAge(), person.getEmail(), person.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }


}

