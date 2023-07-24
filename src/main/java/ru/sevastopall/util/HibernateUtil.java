package ru.sevastopall.util;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import ru.sevastopall.converter.BirthdayConverter;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = getConfiguration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        /*configuration.addAnnotatedClass(User.class);*/
        /*configuration.addAnnotatedClass(Company.class);*/
        configuration.addAttributeConverter(new BirthdayConverter(), true);
        configuration.registerTypeOverride(new JsonBinaryType());
        return configuration;
    }
}
