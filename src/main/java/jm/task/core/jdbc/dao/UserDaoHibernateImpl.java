package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.*;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.persistence.EntityManager;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    Transaction transaction = null;

    private static SessionFactory sessionFactory() {
        return Util.getSessionFactory();
    }


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory().openSession()) {

             transaction = session.beginTransaction();


            Query query = session.createSQLQuery
                    ("CREATE TABLE IF NOT EXISTS users (id serial," +
                            "name varchar(45)," +
                            "lastName varchar(45)" +
                            ",age tinyint)").addEntity(User.class);
            query.executeUpdate();

            transaction.commit();

        }
        catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }


    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);

        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {


        try (Session session = sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);

        }


    }


    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.flush();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);

        }


    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List fromUser = session.createQuery("from User ").list();
            transaction.commit();
            return fromUser;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);

        }

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DELETE from users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);

        }

    }
}
