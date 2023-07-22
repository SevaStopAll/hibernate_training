package ru.sevastopall;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sevastopall.entity.User;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        /*configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());*/
        /*configuration.addAnnotatedClass(User.class);*/
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("Ivan@gmail.com")
                    .firstName("Ivan")
                    .lastName("Ivanov")
                    .birthDate(LocalDate.of(2000, 1, 19))
                    .age(20)
                    .build();
            session.persist(user);


            session.getTransaction().commit();
        }
    }
}
