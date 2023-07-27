package ru.sevastopall.util;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sevastopall.entity.*;

import java.time.LocalDate;
import java.time.Month;

public class TestDataImporter {

    public static void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        Company microsoft = saveCompany(session, "Microsoft");
        Company apple = saveCompany(session, "Apple");
        Company google = saveCompany(session, "Google");

        User billGates = saveUser(session, "Bill", "Gates",
                LocalDate.of(1955, Month.OCTOBER, 28), microsoft);
        User steveJobs = saveUser(session, "Steve", "Jobs",
                LocalDate.of(1955, Month.FEBRUARY, 24), apple);
        User sergeyBrin = saveUser(session, "Sergey", "Brin",
                LocalDate.of(1973, Month.AUGUST, 21), google);
        User timCook = saveUser(session, "Tim", "Cook",
                LocalDate.of(1960, Month.NOVEMBER, 1), apple);
        User dianeGreene = saveUser(session, "Diane", "Greene",
                LocalDate.of(1955, Month.JANUARY, 1), google);

        savePayment(session, billGates, 100);
        savePayment(session, billGates, 300);
        savePayment(session, billGates, 500);

        savePayment(session, steveJobs, 250);
        savePayment(session, steveJobs, 600);
        savePayment(session, steveJobs, 500);

        savePayment(session, timCook, 400);
        savePayment(session, timCook, 300);

        savePayment(session, sergeyBrin, 500);
        savePayment(session, sergeyBrin, 500);
        savePayment(session, sergeyBrin, 500);

        savePayment(session, dianeGreene, 300);
        savePayment(session, dianeGreene, 300);
        savePayment(session, dianeGreene, 300);
    }

    private static Company saveCompany(Session session, String name) {
        Company company = Company.builder()
                .name(name)
                .build();

        session.save(company);

        return company;
    }

    private static User saveUser(Session session, String firstName, String lastName, LocalDate birthDate, Company company) {
        User user = User.builder()
                .company(company)
                .username(firstName + lastName)
                .personalInfo(PersonalInfo.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .birthDate(new Birthday(birthDate))
                        .build())
                .build();

        session.save(user);

        return user;
    }

    private static Payment savePayment(Session session, User user, int amount) {
        Payment payment = Payment.builder()
                .amount(amount)
                .reciever(user)
                .build();

        session.save(payment);

        return payment;
    }
}
