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

@Slf4j
public class HibernateRunner {

    /*private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);*/
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

        User user = User.builder()
                .username("petr2@gmail.ru")
                .personalInfo(PersonalInfo.builder()
                        .lastName("Ivanov")
                        .firstName("Ivan")
                        .build())
                .build();
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                log.trace("Transaction is create, {}", transaction);

                session1.saveOrUpdate(user);
                log.trace("User is persistent state: {}, session {}", user, session1);

                session1.getTransaction().commit();
            }
            log.warn("User is in detached state: {}, session {}", user, session1);

            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
                /*session2.delete(user);*/

/*                session2.merge(user);*/
                session2.getTransaction().commit();
            }
        }
        catch (Exception exception) {
            log.error("Exception occured", exception);
            throw exception;
        }
    }
}
