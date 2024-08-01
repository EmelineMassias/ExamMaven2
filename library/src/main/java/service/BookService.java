package service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.Book;

import java.util.List;

public class BookService {
    public void addBook(Book book, Session HibernateUtil) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Book> getBooks(Session HibernateUtil) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Book").list();
        }
    }
}
