package edu.cepuii.dao.hibernate;

import edu.cepuii.dao.BookDao;
import edu.cepuii.models.Book;
import edu.cepuii.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class HibernateBookDao implements BookDao {

    private final SessionFactory sessionFactory;

    public HibernateBookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book ", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByPersonId(int personId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, personId).getBooks();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getById(int bookId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book WHERE bookId= :bookId", Book.class)
                .setParameter("bookId", bookId)
                .getResultList().stream().findAny();
    }

    @Transactional
    @Override
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    @Override
    public void update(Book book, int bookId) {
        Session session = sessionFactory.getCurrentSession();
        Book bookForUpdate = session.get(Book.class, bookId);
        bookForUpdate.setAuthor(book.getAuthor());
        bookForUpdate.setTitle(book.getTitle());
        bookForUpdate.setYearOfPublic(book.getYearOfPublic());
    }

    @Transactional
    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
    }

    @Transactional
    @Override
    public void cleanPersonId(int bookId) {
        Session session = sessionFactory.getCurrentSession();
        session.get(Book.class, bookId).setOwner(null);
    }

    @Transactional
    @Override
    public void updatePersonId(int bookId, Integer personId) {
        Session session = sessionFactory.getCurrentSession();
        session.get(Book.class, bookId).setOwner(session.get(Person.class, personId));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Person> getBookOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Book.class, id).getOwner());
    }
}
