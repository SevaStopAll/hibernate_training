package ru.sevastopall;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sevastopall.entity.Payment;
import ru.sevastopall.util.HibernateUtil;
import ru.sevastopall.util.TestDataImporter;

public class HibernateRunnerCallbacksListeners {

    public static void main(String[] args) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()) {


            session.beginTransaction();

            TestDataImporter.importData(sessionFactory);

/*            Payment payment = session.find(Payment.class, 1L);*/

            session.getTransaction().commit();
        }
    }
}
