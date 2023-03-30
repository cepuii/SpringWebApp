package edu.cepuii.util;

import edu.cepuii.dao.PersonDao;
import edu.cepuii.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

        if (personDao.getByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Already exist");
        }
    }
}
