package edu.cepuii.dao;

import edu.cepuii.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao {

    List<Person> getAll();

    Optional<Person> getById(int id);

    Optional<Person> getByFullName(String fullName);


    void save(Person person);

    void update(int id, Person person);

    void delete(int id);

}
