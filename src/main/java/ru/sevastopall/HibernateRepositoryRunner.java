package ru.sevastopall;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sevastopall.dto.UserCreateDto;
import ru.sevastopall.entity.PersonalInfo;
import ru.sevastopall.entity.Role;
import ru.sevastopall.interceptor.TransactionInterceptor;
import ru.sevastopall.mapper.CompanyReadMapper;
import ru.sevastopall.mapper.UserCreateMapper;
import ru.sevastopall.mapper.UserReadMapper;
import ru.sevastopall.repository.CompanyRepository;
import ru.sevastopall.repository.UserRepository;
import ru.sevastopall.service.UserService;
import ru.sevastopall.util.HibernateUtil;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.time.LocalDate;

public class HibernateRepositoryRunner {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class}, ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)));
            /*session.beginTransaction();*/

            CompanyRepository companyRepository = new CompanyRepository(session);

            CompanyReadMapper companyReadMapper = new CompanyReadMapper();
            UserReadMapper userReadMapper = new UserReadMapper(companyReadMapper);
            UserCreateMapper userCreateMapper = new UserCreateMapper(companyRepository);

            UserRepository userRepository = new UserRepository(session);
            /*UserService userService = new UserService(userRepository, userReadMapper, userCreateMapper);*/
            TransactionInterceptor transactionInterceptor = new TransactionInterceptor(sessionFactory);

            UserService userService = new ByteBuddy()
                    .subclass(UserService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(transactionInterceptor))
                    .make()
                    .load(UserService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);

            userService.findById(1L).ifPresent(System.out::println);


            UserCreateDto UserCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstName("Liza")
                            .lastName("Stepanova")
                            /*.birthDate(LocalDate.now())*/
                            .build(),
                    "liza3@gmail.com",
                    null,
                    Role.USER,
                    1
            );
            userService.create(UserCreateDto);


          /*  session.getTransaction().commit();*/
        }
    }
}
