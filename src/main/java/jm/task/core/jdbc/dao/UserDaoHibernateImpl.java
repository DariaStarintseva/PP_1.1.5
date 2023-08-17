package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private static final String create  = "CREATE TABLE IF NOT EXISTS mytest.users " +
            "(id BIGINT AUTO_INCREMENT PRIMARY KEY , " +
            "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
            "age TINYINT NOT NULL)";
    private static final String drop = "DROP TABLE IF EXISTS mytest.users;";
    private static final String clean = "DELETE FROM mytest.users;";
    private static final String get = "FROM User";
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery(create).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery(drop).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        transaction.commit();
        session.close();
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {

        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(get);
        List<User> userList = query.list();
        transaction.commit();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery(clean).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();

    }
}
