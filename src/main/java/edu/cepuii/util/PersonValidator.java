package edu.cepuii.util;

import edu.cepuii.dao.PersonDao;
import edu.cepuii.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDao personDao;

    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Optional<Person> byEmail = personDao.getByEmail(person.getEmail());

        if (byEmail.isPresent()) {
            errors.rejectValue("email", "", "This email is already taken");
        }

    }
}
