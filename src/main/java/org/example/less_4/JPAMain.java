package org.example.less_4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.example.less_4.entity.Author;
import org.example.less_4.entity.AuthorBook;
import org.example.less_4.entity.Book;

public class JPAMain {

    // employee(id, department_id)
    // department(id)

    // Employee e = session.find(Employee.class, 1L);
    // Department d2 = session.find(Department.class, 2L);
    // e.setDepartment(d2);
    // session.merge(e)

    public static void main(String[] args) {
        // ORM - Object Relation Model
        // JPA - Jakarta Persistence API -
        // Hibernate - реализация JPA
        // EclipseLink - тоже реализация JPA

        // connection poll
        // hikari

        Configuration configuration = new Configuration();
        configuration.configure(); // !!! иначе cfg.xml не прочитается
        // === System.out.println(configuration.toString());
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            // sessionFactory <-> connection

            // === withSession(sessionFactory);
            // === withSessionCRUD(sessionFactory);


//      withSessionCRUD(sessionFactory);
        }
    }

    private static void withSession(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Author author = new Author();
            // author.setId(1);
            author.setF_name("Сидор");
            author.setL_name("Сидоров");
            /*
            Book book = new Book();
            book.setId(1);
            book.setName("Book");

            AuthorBook authorBook = new AuthorBook();
            authorBook.setId(1L); // id
            authorBook.setAuthor(author); // author_id
            authorBook.setBook(book); // book_id

            Transaction tx = session.beginTransaction();

             */
            session.persist(author);
            // session.persist(book);

            /*
            if (true) {
                throw new RuntimeException();
            }

            session.persist(authorBook);
            tx.commit();

             */
        }

//    try (Session session = sessionFactory.openSession()) {
//      Author author = session.find(Author.class, 2L);
//      System.out.println(author);
//
//      for (Book book : author.getBooks()) {
//        System.out.println(book);
//      }
//    }
    }

    // create read update delete
    private static void withSessionCRUD(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            // session <-> statement

            Author author = session.find(Author.class, 1);
            System.out.println("Author(1) = " + author);
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Author author = new Author();
            // author.setId(22);
            author.setF_name("Михаил");
            author.setF_name("Иванов");

            session.persist(author); // insert
            tx.commit();
        }

        try (Session session = sessionFactory.openSession()) {
            Author toUpdate = session.find(Author.class, 22);
            session.detach(toUpdate);
            toUpdate.setF_name("UPDATED");

            Transaction tx = session.beginTransaction();
//      session.merge(toUpdate); // update
            tx.commit();
        }

        try (Session session = sessionFactory.openSession()) {
            Author toDelete = session.find(Author.class, 1);

            Transaction tx = session.beginTransaction();
            session.remove(toDelete); // delete
            tx.commit();
        }



    }

}

