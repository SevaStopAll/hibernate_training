package ru.sevastopall.repository;

import org.hibernate.SessionFactory;
import ru.sevastopall.entity.Company;

import javax.persistence.EntityManager;


public class CompanyRepository extends RepositoryBase<Integer, Company> {

    public CompanyRepository(EntityManager entityManager) {

        super(Company.class, entityManager);
    }

}
