package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory session = null;

    public UserDaoHibernateImpl() {
        this.session = Util.getSessionFactory();

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";

        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query query = session.createSQLQuery(sql).addEntity(User.class);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println("Failed to create table: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Failed to create table: " + e.getMessage());
        }
    }


    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query query = session.createSQLQuery(sql);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println("Failed to drop table: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Failed to drop table: " + e.getMessage());
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);

        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(user);
                transaction.commit(); // Коммит, если успешно
            } catch (Exception e) {
                transaction.rollback(); // Откат в случае исключения
                System.out.println("Failed to save user: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Failed to save user: " + e.getMessage());
        }
    }


    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                    transaction.commit();
                } else {
                    System.out.println("User  with id \" + id + \" not found.");
                    transaction.rollback();
                }
            } catch (Exception e) {
                transaction.rollback();
                System.out.println("Failed to remove user: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Failed to remove user: " + e.getMessage());
        }
    }



    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query query = session.createSQLQuery(sql).addEntity(User.class);
                users = query.getResultList();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println("Failed to get all users: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Failed to get all users: " + e.getMessage());
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                String sql = "DELETE FROM users";
                Query query = session.createSQLQuery(sql);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println("Failed to clean table: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Failed to clean table: " + e.getMessage());
        }
    }
}

