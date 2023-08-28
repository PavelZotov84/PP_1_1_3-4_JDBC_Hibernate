package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(40), lastName VARCHAR(40), age INT(3))")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Таблица успешно создана");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            System.out.println("Таблица успешно удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, int age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, (byte) age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("User c id " + id + " успешно удалён");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            transaction.commit();
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("delete from users").executeUpdate();
            transaction.commit();
            System.out.println("Все данные удалены");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
