package ru.sevastopall;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import ru.sevastopall.entity.Payment;
import ru.sevastopall.util.HibernateUtil;
import ru.sevastopall.util.TestDataImporter;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class HibernateRunnerTrLks {

    @Transactional
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            /*TestDataImporter.importData(sessionFactory);*/
           /* session.doWork(connection -> connection.setAutoCommit(false));*/

         /*       session.setDefaultReadOnly(true);*/
                /*Transaction transaction = session.beginTransaction();*/


            session.createQuery("select p from Payment  p", Payment.class);
                    /*.setLockMode(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
                    .setHint("javax.persistence.lock.timeout", 5000)
                    .list();*/



            Payment payment = session.find(Payment.class, 1L);
            /*payment.setAmount(payment.getAmount() + 10);*/

          /*  session.getTransaction().commit();*/


        }
    }
}
