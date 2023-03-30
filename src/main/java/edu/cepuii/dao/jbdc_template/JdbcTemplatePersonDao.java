package edu.cepuii.dao.jbdc_template;

import edu.cepuii.dao.PersonDao;
import edu.cepuii.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

//@Component
public class JdbcTemplatePersonDao implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplatePersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> getById(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?", new BeanPropertyRowMapper<>(Person.class), new Object[]{id})
                .stream().findAny();
    }
    public Optional<Person> getByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?", new BeanPropertyRowMapper<>(Person.class), new Object[]{fullName})
                .stream().findAny();
    }


    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (full_name, year_of_birth) VALUES (?,?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET full_name=?, year_of_birth=? WHERE person_id=?",
                person.getFullName(), person.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", id);
    }


}

