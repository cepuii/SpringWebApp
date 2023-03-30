package edu.cepuii.dao.hibernate;

import edu.cepuii.dao.PersonDao;
import edu.cepuii.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class HibernatePersonDao implements PersonDao {

    private final SessionFactory sessionFactory;

    public HibernatePersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Person", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Person> getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Person.class, id));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Person> getByFullName(String fullName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Person WHERE fullName= :fullName", Person.class)
                .setParameter("fullName", fullName)
                .getResultList().stream().findAny();
    }

    @Transactional
    @Override
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    @Override
    public void update(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person personForUpdate = session.get(Person.class, id);
        personForUpdate.setFullName(person.getFullName());
        personForUpdate.setYearOfBirth(person.getYearOfBirth());
    }

    @Transactional
    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }
}
