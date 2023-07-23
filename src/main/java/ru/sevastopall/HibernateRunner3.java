package ru.sevastopall;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sevastopall.entity.Birthday;
import ru.sevastopall.entity.Company;
import ru.sevastopall.entity.PersonalInfo;
import ru.sevastopall.entity.User;
import ru.sevastopall.util.HibernateUtil;

import java.time.LocalDate;

public class HibernateRunner3 {

    public static void main(String[] args) {
        Company google = Company.builder()
                .name("Google")
                .build();
        User user = User.builder()
                .username("1dima@gmail.ru")
                .personalInfo(PersonalInfo.builder()
                        .lastName("Ivanov")
                        .firstName("Ivan")
                        .birthDate(new Birthday(LocalDate.of(1999, 12, 12)))
                        .build())
                .company(google)
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();

                User user1 = session1.get(User.class, 1L);
                Company company = user1.getCompany();
                String name = company.getName();
           /*     session1.save(google);
                session1.save(user);*/
                Object ourObject = Hibernate.unproxy(company);


                session1.getTransaction().commit();
            }
    }
    }
}
