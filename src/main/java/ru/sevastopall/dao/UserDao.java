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
     * ���������� ���� �����������
     */
    public List<User> findAll(Session session) {
        return session.createQuery("select u from User u", User.class)
                .list();
    }

    /**
     * ���������� ���� ����������� � ��������� ������
     */
    public List<User> findAllByFirstName(Session session, String firstName) {

        return session.createQuery("select u from User u where u.personalInfo.firstName = :firstName", User.class)
                .setParameter("firstName", firstName)
                .list();
    }

    /**
     * ���������� ������ {limit} �����������, ������������� �� ���� �������� (� ������� �����������)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {

        return session.createQuery("select u from User u order by u.personalInfo.birthDate", User.class)
                .setMaxResults(limit)
                .list();
    }

    /**
     * ���������� ���� ����������� �������� � ��������� ���������
     */
    public List<User> findAllByCompanyName(Session session, String companyName) {

        return session.createQuery("select u from Company c join c.users u where c.name = :companyName", User.class)
                .setParameter("companyName", companyName)
                .list();
    }

    /**
     * ���������� ��� �������, ���������� ������������ �������� � ���������� ������,
     * ������������� �� ����� ����������, � ����� �� ������� �������
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return Collections.emptyList();
    }

    /**
     * ���������� ������� �������� ���������� � ���������� ������ � ��������
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        return Double.MAX_VALUE;
    }

    /**
     * ���������� ��� ������ ��������: ��������, ������� �������� ���� � �����������. �������� ����������� �� ��������.
     */
    public List<Object[]> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return Collections.emptyList();
    }

    /**
     * ���������� ������: ��������� (������ User), ������� ������ ������, �� ������ ��� ��� �����������, ��� ������� ������ ������
     * ������ �������� ������� ������ ���� �����������
     * ����������� �� ����� ����������
     */
    public List<Object[]> isItPossible(Session session) {
        return Collections.emptyList();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
