package ru.sevastopall.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;
import ru.sevastopall.entity.User;
import ru.sevastopall.entity.UserChat;

import java.util.List;
import java.util.Map;

public class HibernateRunnerH1 {
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

 /*           TestDataImporter.importData(sessionFactory);*/

            session.beginTransaction();
            /*session.enableFetchProfile("withCompanyAndPayments");*/

            RootGraph<User> entityGraph = session.createEntityGraph(User.class);
            entityGraph.addAttributeNodes("company", "userChats");
            SubGraph<UserChat> userChats = entityGraph.addSubgraph("userChats", UserChat.class);
            userChats.addAttributeNodes("chat");

            /*RootGraph<?> withCompanyAndChat = session.getEntityGraph("WithCompanyAndChat");*/

            Map<String, Object> properties = Map.of(
                    /*GraphSemantic.LOAD.getJpaHintName(), withCompanyAndChat*/
                    GraphSemantic.LOAD.getJpaHintName(), entityGraph
            );
            /*User user = session.find(User.class, 1L, properties);
            System.out.println(user.getCompany().getName());
            System.out.println(user.getUserChats().size());*/



            List<User> users = session.createQuery(
                    "select u from User u join fetch u.payments join fetch u.company", User.class)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), entityGraph)
                    .list();
            users.forEach(user -> System.out.println(user.getUserChats().size()));
            users.forEach(user -> System.out.println(user.getCompany().getName()));

            session.getTransaction().commit();
        }
    }
}
