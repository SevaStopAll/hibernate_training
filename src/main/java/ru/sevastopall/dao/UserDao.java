package ru.sevastopall.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import ru.sevastopall.dto.PaymentFilter;
import ru.sevastopall.entity.*;


import java.util.List;

import static ru.sevastopall.entity.QCompany.*;
import static ru.sevastopall.entity.QPayment.*;
import static ru.sevastopall.entity.QUser.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    /**
     * 1 Закомментированный вариант - HQL.
     * 2 Закомментированный вариант - Criteria API
     * Активный вариант QueryDSL
     */

    private static final UserDao INSTANCE = new UserDao();

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
        /*return session.createQuery("select u from User u", User.class)
                .list();*/



        /*CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> user = criteria.from(User.class);

        criteria.select(user);

        return session.createQuery(criteria)
                .list();*/


        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .fetch();

    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<User> findAllByFirstName(Session session, String firstName) {

        /*return session.createQuery("select u from User u" +
                        " where u.personalInfo.firstName = :firstName", User.class)
                .setParameter("firstName", firstName)
                .list();*/




       /* CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> user = criteria.from(User.class);

        criteria.select(user).where(
                criteriaBuilder.equal(user.get(User_.personalInfo).get(PersonalInfo_.FIRST_NAME), firstName));

        return session.createQuery(criteria)
                .list();*/

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.personalInfo.firstName.eq(firstName))
                .fetch();

    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения (в порядке возрастания)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
/*        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> user = criteria.from(User.class);

        criteria.select(user).orderBy(criteriaBuilder.asc(user.get(User_.personalInfo).get(PersonalInfo_))

        return session.createQuery(criteria)
                .setMaxResults(limit)
                .list();*/



        return session.createQuery("select u from User u" +
                        " order by u.personalInfo.birthDate", User.class)
                .setMaxResults(limit)
                .list();
    }

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    public List<User> findAllByCompanyName(Session session, String companyName) {

        /*CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<Company> company = criteria.from(Company.class);
        MapJoin<Company, String, User> users = company.join(Company_.users);

        criteria.select(users).where(
                criteriaBuilder.equal(company.get(Company_.name), companyName)
        );
        return session.createQuery(criteria)
                .list();*/



        /*return session.createQuery("select u from Company c" +
                        " join c.users u " +
                        "where c.name = :companyName", User.class)
                .setParameter("companyName", companyName)
                .list();*/

        return new JPAQuery<User>(session)
                .select(user)
                .from(company)
                .join(company.users, user)
                .where(company.name.eq(companyName))
                .fetch();
    }

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        /*return session.createQuery("select p from Payment p" +
                        " join p.receiver u join u.company c " +
                        "where c.name = :companyName " +
                        "order by u.personalInfo.firstName asc, p.amount asc", Payment.class)
                .setParameter("companyName", companyName)
                .list();*/




        /*CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Payment> criteria = criteriaBuilder.createQuery(Payment.class);

        Root<Payment> payment = criteria.from(Payment.class);
        Join<Payment, User> user = payment.join(Payment_.receiver);
        Join<User, Company> company = user.join(User_.company);

        criteria.select(payment).where(
                criteriaBuilder.equal(company.get(Company_.name), companyName)
        )
                .orderBy(
                        criteriaBuilder.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstName)),
                        criteriaBuilder.asc(payment.get(Payment_.amount))
                );
        return session.createQuery(criteria)
                .list();*/



        return new JPAQuery<Payment>(session)
                .select(payment)
                .from(payment)
                .join(payment.receiver, user)
                .join(user.company, company)
                .where(company.name.eq(companyName))
                .orderBy(user.personalInfo.firstName.asc(), payment.amount.asc())
                .fetch();

    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, PaymentFilter filter) {
        /*return session.createQuery("select avg(p.amount) from Payment p" +
                        " join p.receiver u" +
                        " where u.personalInfo.firstName = :firstName" +
                        " AND u.personalInfo.lastName = :lastName", Double.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .uniqueResult();*/



       /* CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Double> criteria = criteriaBuilder.createQuery(Double.class);

        Root<Payment> payment = criteria.from(Payment.class);

        Join<Payment, User> user = payment.join(Payment_.receiver);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(criteriaBuilder.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstName), firstName));
        }
        if (lastName != null) {
            predicates.add(criteriaBuilder.equal(user.get(User_.personalInfo).get(PersonalInfo_.lastName), lastName));
        }

        criteria.select(criteriaBuilder.avg(payment.get(Payment_.amount))).where(
             predicates.toArray(Predicate[]::new)
        );

        return session.createQuery(criteria)
                .uniqueResult();*/


   /*     return new JPAQuery<Double>(session)
                .select(payment.amount.avg())
                .from(payment)
                .join(payment.receiver, user)
                .where(user.personalInfo.firstName.eq(firstName)
                        .and(user.personalInfo.lastName.eq(lastName)))
                .fetchOne();*/

        Predicate predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.personalInfo.firstName::eq)
                .add(filter.getLastName(), user.personalInfo.lastName::eq)
                .buildAnd();

        return new JPAQuery<Double>(session)
                .select(payment.amount.avg())
                .from(payment)
                .join(payment.receiver, user)
                .where(predicate)
                .fetchOne();
    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<com.querydsl.core.Tuple> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        /*return session.createQuery("select c.name, avg(p.amount) from Company c" +
                        " join c.users u join u.payments p" +
                        " group by c.name" +
                        " order by c.name", Object[].class)
                .list();*/




        /*CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CompanyDto> criteria = criteriaBuilder.createQuery(CompanyDto.class);

        Root<Company> company = criteria.from(Company.class);
        MapJoin<Company, String, User> user = company.join(Company_.users, JoinType.INNER);
        ListJoin<User, Payment> payment = user.join(User_.payments);

        criteria.select(
                criteriaBuilder.construct(CompanyDto.class,
                company.get(Company_.name),
                criteriaBuilder.avg(payment.get(Payment_.amount)))
        )
                .groupBy(company.get(Company_.name))
                .orderBy(criteriaBuilder.asc(company.get(Company_.name)));

        return session.createQuery(criteria)
                .list();*/

        return new JPAQuery<Tuple>(session)
                .select(company.name, payment.amount.avg())
                .from(company)
                .join(company.users, user)
                .join(user.payments, payment)
                .groupBy(company.name)
                .orderBy(company.name.asc())
                .fetch();


    }

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
    public List<com.querydsl.core.Tuple> isItPossible(Session session) {

       /* return session.createQuery("select u, avg(p.amount) from User u" +
                        " join u.payments p" +
                        " group by u.id" +
                        " having avg(p.amount) > (select avg(p.amount) from Payment p)" +
                        " order by u.personalInfo.firstName", Object[].class)
                .list();*/




       /* CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteria = criteriaBuilder.createQuery(Tuple.class);

        Root<User> user = criteria.from(User.class);
        ListJoin<User, Payment> payments = user.join(User_.payments);

        Subquery<Double> subquery = criteria.subquery(Double.class);
        Root<Payment> paymentSubquery = subquery.from(Payment.class);


        criteria.select(
                criteriaBuilder.tuple(
                        user,
                        criteriaBuilder.avg(payments.get(Payment_.amount))
                )
        )
                .groupBy(user.get(User_.id))
                .having(criteriaBuilder.gt(
                        criteriaBuilder.avg(payments.get(Payment_.amount)), subquery.select(criteriaBuilder.avg(paymentSubquery.get(Payment_.amount)))

                ))
                .orderBy(criteriaBuilder.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstName)));

        return session.createQuery(criteria)
                .list();*/



        return new JPAQuery<Tuple>(session)
                .select(user, payment.amount.avg())
                .from(user)
                .join(user.payments, payment)
                .groupBy(user.id)
                .having(payment.amount.avg().gt(
                        new JPAQuery<Double>(session)
                                .select(payment.amount.avg())
                                .from(payment)))
                .orderBy(user.personalInfo.firstName.asc())
                .fetch();

    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
