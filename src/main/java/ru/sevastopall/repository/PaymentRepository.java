package ru.sevastopall.repository;

import org.hibernate.SessionFactory;
import ru.sevastopall.entity.Payment;

import javax.persistence.EntityManager;


public class PaymentRepository extends RepositoryBase<Long, Payment> {

    public PaymentRepository(EntityManager entityManager) {
        super(Payment.class, entityManager);
    }

}
