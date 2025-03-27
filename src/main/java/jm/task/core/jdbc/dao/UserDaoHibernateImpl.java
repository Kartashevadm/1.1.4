package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        Query query = session.createSQLQuery(sql).addEntity(User.class);

        transaction.commit();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS users";
        Query query = session.createSQLQuery(sql);

        transaction.commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
    Session session = getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    User user = new User();
    user.setName(name);
    user.setLastName(lastName);
    user.setAge(age);
    session.save(user);

    transaction.commit();
    session.close();

    }

    @Override
    public void removeUserById(long id) {
    Session session = getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    User user = (User) session.get(User.class, id);
    session.delete(user);

    transaction.commit();
    session.close();

    }

    @Override
    public List<User> getAllUsers(){
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User");

        transaction.commit();
        session.close();
        return List.of();
    }


    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DELETE FROM users";
        Query query = session.createSQLQuery(sql);

        transaction.commit();
        session.close();

    }
}
