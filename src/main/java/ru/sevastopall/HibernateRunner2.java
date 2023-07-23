package ru.sevastopall;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sevastopall.entity.Birthday;
import ru.sevastopall.entity.PersonalInfo;
import ru.sevastopall.entity.User;
import ru.sevastopall.util.HibernateUtil;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner2 {

    /*private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);*/

    public static void main(String[] args) {
        User user = User.builder()
                .username("1dima@gmail.ru")
                .personalInfo(PersonalInfo.builder()
                        .lastName("Ivanov")
                        .firstName("Ivan")
                        .birthDate(new Birthday(LocalDate.of(1999, 12, 12)))
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
