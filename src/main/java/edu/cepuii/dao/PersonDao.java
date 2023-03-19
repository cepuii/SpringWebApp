package edu.cepuii.dao;

import edu.cepuii.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {

    private static int peopleId = 100;
    private final List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(peopleId++, "cepuii"));
        people.add(new Person(peopleId++, "name2"));
        people.add(new Person(peopleId++, "name3"));
        people.add(new Person(peopleId++, "name4"));
    }

    public List<Person> getAll(){
        return people;
    }

    public Person getById(int id){
        return people.stream().filter(person -> person.getId()==id).findFirst().orElse(null);
    }

}

