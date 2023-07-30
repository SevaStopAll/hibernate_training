package ru.sevastopall.util;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import ru.sevastopall.converter.BirthdayConverter;
import ru.sevastopall.entity.Audit;
import ru.sevastopall.entity.Revision;
import ru.sevastopall.interceptor.GlobalInterceptor;
import ru.sevastopall.listener.AuditTableListener;

import java.util.EventListener;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = getConfiguration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
      /*  registerListeners(sessionFactory);*/

        return sessionFactory;
    }

    private static void registerListeners(SessionFactory sessionFactory) {
        SessionFactoryImpl sessionFactoryImpl = sessionFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry service = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
        service.appendListeners(EventType.PRE_INSERT, new AuditTableListener());
        service.appendListeners(EventType.PRE_DELETE, new AuditTableListener());
    }

    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(Audit.class);
        configuration.addAnnotatedClass(Revision.class);
        /*configuration.addAnnotatedClass(User.class);*/
        /*configuration.addAnnotatedClass(Company.class);*/
        configuration.addAttributeConverter(new BirthdayConverter(), true);
        configuration.registerTypeOverride(new JsonBinaryType());
        /*configuration.setInterceptor(new GlobalInterceptor());*/
        return configuration;
    }
}
