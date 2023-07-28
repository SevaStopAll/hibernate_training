package ru.sevastopall.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import ru.sevastopall.entity.Payment;
import ru.sevastopall.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    /**
     * ¬озвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
        return session.createQuery("select u from User u", User.class)
                .list();
    }

    /**
     * ¬озвращает всех сотрудников с указанным именем
     */
    public List<User> findAllByFirstName(Session session, String firstName) {

        return session.createQuery("select u from User u where u.personalInfo.firstName = :firstName", User.class)
                .setParameter("firstName", firstName)
                .list();
    }

    /**
     * ¬озвращает первые {limit} сотрудников, упор€доченных по дате рождени€ (в пор€дке возрастани€)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {

        return session.createQuery("select u from User u order by u.personalInfo.birthDate", User.class)
                .setMaxResults(limit)
                .list();
    }

    /**
     * ¬озвращает всех сотрудников компании с указанным названием
     */
    public List<User> findAllByCompanyName(Session session, String companyName) {

        return session.createQuery("select u from Company c join c.users u where c.name = :companyName", User.class)
                .setParameter("companyName", companyName)
                .list();
    }

    /**
     * ¬озвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упор€доченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return Collections.emptyList();
    }

    /**
     * ¬озвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        return Double.MAX_VALUE;
    }

    /**
     * ¬озвращает дл€ каждой компании: название, среднюю зарплату всех еЄ сотрудников.  омпании упор€дочены по названию.
     */
    public List<Object[]> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return Collections.emptyList();
    }

    /**
     * ¬озвращает список: сотрудник (объект User), средний размер выплат, но только дл€ тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * ”пор€дочить по имени сотрудника
     */
    public List<Object[]> isItPossible(Session session) {
        return Collections.emptyList();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
