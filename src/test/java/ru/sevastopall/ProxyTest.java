package ru.sevastopall;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import ru.sevastopall.entity.Company;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    @Test
    void testDinamic() {
        Company company = new Company();
        Proxy.newProxyInstance(company.getClass().getClassLoader(), company.getClass().getInterfaces(),
                (proxy, method , args) -> method.invoke(company, args));
    }
}
