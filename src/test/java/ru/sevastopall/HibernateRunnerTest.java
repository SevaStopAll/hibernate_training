package ru.sevastopall;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.sevastopall.entity.*;
import ru.sevastopall.util.HibernateTestUtil;
import ru.sevastopall.util.HibernateUtil;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

class HibernateRunnerTest {

    @Test
    void checkH2() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Company company = Company.builder()
                    .name("Google")
                    .build();

            session.save(company);

            Programmer programmer = Programmer.builder()
                    .username("ivanov@yandex.ru")
                    .language(Language.JAVA)
                    .company(company)
                    .build();
            session.save(programmer);

            Manager manager1 = Manager.builder()
                    .username("svaaa@rambler.com")
                    .projectName("Starter")
                    .company(company)
                    .build();

            session.save(manager1);


            Programmer programmer1 = Programmer.builder()
                    .username("petriv@yandex.ru")
                    .language(Language.RUBY)
                    .company(company)
                    .build();
            session.save(programmer1);

            session.getTransaction().commit();
        }
    }

    @Test
    void orderingTest() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.get(Company.class, 1);
            company.getUsers().forEach((k, v) -> System.out.println(v));

            session.getTransaction().commit();
        }
    }

    @Test
    void localInfo() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.get(Company.class, 1);
           /* company.getLocales().add(LocaleInfo.of("ru", "Описание на русском"));
            company.getLocales().add(LocaleInfo.of("en", "Description in English"));
*/
            session.getTransaction().commit();
        }

    }

    @Test
    void checkManyToMany() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1L);
            Chat chat = session.get(Chat.class, 1L);

            /*UserChat usersChat = UserChat.builder()
                    .createdAt(Instant.now())
                    .createdBy(user.getUsername())
                    .build();

            usersChat.setUser(user);
            usersChat.setChat(chat);

            session.save(usersChat);*/

            /*user.getChats().clear();*/
           /* Chat chat = Chat.builder()
                    .name("chat")
                    .build();

            user.addChat(chat);
            session.save(chat);*/

            session.getTransaction().commit();

        }
    }

    @Test
    void checkOneToOne() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

           /* User user = User.builder()
                    .username("test4@gmail.com")
                    .company(session.get(Company.class, 1))
                    .build();
            Profile profile = Profile.builder()
                    .street("Right 18")
                    .language("RU")
                    .build();

            profile.setUser(user);
            session.save(user);*/

            session.getTransaction().commit();

        }
    }

    @Test
    void checkOrphanRemoval() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.getReference(Company.class, 1);
            /*company.getUsers().removeIf(user -> user.getId().equals(2L));*/

            session.getTransaction().commit();
        }
    }

    @Test
    void checkLazyInitialisation() {
        Company company = null;
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            company = session.get(Company.class, 1);


            session.getTransaction().commit();
        }
        /*Set<User> users = company.getUsers();*/
        /*System.out.println(users.size());*/
    }

    @Test
    void deleteCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 3);
        session.delete(company);

        session.getTransaction().commit();
    }

    @Test
    void addUserToNewCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = Company.builder()
                .name("Facebook")
                .build();

      /*  User user = User.builder()
                .username("Sveta@gmail.com")
                .build();

        company.addUser(user);*/

        session.save(company);

        session.getTransaction().commit();
    }

    @Test
    void oneToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.get(Company.class, 1);
        System.out.println(company.getUsers());

        session.getTransaction().commit();
    }

    @Test
    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.getString("firstname");
        resultSet.getString("lastname");

        Class<User> userClass = User.class;

        Constructor<User> userClassConstructor = userClass.getConstructor();
        User user = userClassConstructor.newInstance();
        Field usernameField = userClass.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(user, resultSet.getString("username"));


    }
}
    /*@Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .build();

        String sql = """
                insert into 
                %s
                (%s)
                values
                (%s)
                """;

        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();
        String columnNames = Arrays.stream(declaredFields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(", "));

        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));

        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            preparedStatement.setObject(1, declaredField.get(user));
        }

    }

}*/


/*
    CREATE TABLE company(
        id SERIAL PRIMARY KEY,
        name varchar(64) NOT NULL UNIQUE
);

        CREATE TABLE users(
        id BIGSERIAL PRIMARY KEY,
        username varchar(128),
        firstName varchar(128),
        lastName varchar(128),
        birth_date DATE,
        role varchar(32),
        company_id INT REFERENCES company(id) ON DELETE CASCADE,
        info JSONB
        );

        create table profile(
        id BIGSERIAL PRIMARY KEY,
        user_id BIGINT NOT NULL UNIQUE REFERENCES users (id),
        street varchar(128),
        language CHAR(2)
        );

        CREATE TABLE chat (
        id BIGSERIAL PRIMARY KEY,
        name varchar(64) not null unique
        );

        CREATE TABLE users_chat(
        user_id BIGINT references users(id),
        chat_id BIGINT references chat(id),
        PRIMARY KEY (user_id, chat_id)
        )


        create sequence users_id_seq
        owned by users.id;

        INSERT INTO users(username, firstName, lastName, company_id)
        VALUES ('Katya@gmail.cim', 'Katya', 'Katina', 1);

        INSERT INTO users(username, firstName, lastName, company_id)
        VALUES ('Ulyana@gmail.cim', 'Ulyana', 'Markova', 1);

        DROP TABLE users;

        DROP TABLE profile*/
