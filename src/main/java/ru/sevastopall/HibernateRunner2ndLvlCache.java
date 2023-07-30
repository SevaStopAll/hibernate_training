package ru.sevastopall;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import ru.sevastopall.entity.Payment;
import ru.sevastopall.entity.User;
import ru.sevastopall.util.HibernateUtil;

import java.util.List;

public class HibernateRunner2ndLvlCache {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            User user = null;
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                user = session.find(User.class, 1L);
                user.getCompany().getName();
                user.getUserChats().size();
                User user2 = session.find(User.class, 1L);

                List<Payment> paymentList = session.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
                                .setParameter("userId", 1L)
                        .setCacheable(true)
                        /*.setCacheRegion("queries")*/
                        /*.setHint(QueryHints.CACHEABLE, true)*/
                                        .getResultList();
                session.getTransaction().commit();
            }

            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                User user2 = session1.find(User.class, 1L);
                user2.getCompany().getName();
                user2.getUserChats().size();

                List<Payment> paymentList = session1.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true)
                        /*.setCacheRegion("queries")*/
                        /*.setHint(QueryHints.CACHEABLE, true)*/
                        .getResultList();

                session1.getTransaction().commit();
            }
        }
    }
}
