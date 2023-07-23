package ru.sevastopall;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sevastopall.converter.BirthdayConverter;
import ru.sevastopall.entity.Birthday;
import ru.sevastopall.entity.PersonalInfo;
import ru.sevastopall.entity.Role;
import ru.sevastopall.entity.User;
import ru.sevastopall.util.HibernateUtil;

import java.time.LocalDate;


public class HibernateRunner {


    public static void main(String[] args) {

        /*try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("2Ivan@gmail.com")
                    .firstName("Ivan")
                    .lastName("Ivanov")
                    .info("""
                            {
                                "name" : "Ivan",
                                "id" : 25
                            }
                            """)
                    .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
                    .role(Role.ADMIN)
                    .build();
            session.persist(user);


            session.getTransaction().commit();
        }*/
    }
}
