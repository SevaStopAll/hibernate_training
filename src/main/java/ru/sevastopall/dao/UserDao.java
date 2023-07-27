package ru.sevastopall.dao;

import org.hibernate.Session;
import ru.sevastopall.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDao {
    private static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
        return session.createQuery("select u from User u", User.class)
                .list();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */

    public List<User> findAllByFirstName(Session session, String firstName) {
        return session.createQuery("select u from User u" +
                        " where u.personalInfo.firstName = :firstName", User.class)
                .setParameter("firstName", firstName)
                .list();
    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения по возрастанию
     */

    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return session.createQuery("select u from User u order by u.personalInfo.birthDate", User.class)
                .setMaxResults(limit)
                //.setFirstResult(offset)
                .list();
    }

}
