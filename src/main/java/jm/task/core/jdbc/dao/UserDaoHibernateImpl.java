package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.*;
import org.hibernate.SessionFactory;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

private final SessionFactory sessionFactory = Util.getInstance().getSessionFactory();

    private SessionFactory sessionFactory() {
        return sessionFactory;
    }

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id serial," +
                    "name varchar(45)," + "lastName varchar(45)" + ",age tinyint)").addEntity(User.class)
                    .executeUpdate();

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }


    }



    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory().openSession()) {
            session.beginTransaction();
            List <User> fromUser = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
            return fromUser;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE from users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
