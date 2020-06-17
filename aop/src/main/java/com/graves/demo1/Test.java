package com.graves.demo1;

/**
 * @author Graves
 * @DESCRIPTION
 * @create 2020/6/17
 */
public class Test {

    public static void main(String[] args) {
        ProxyInvocationHandler pih = new ProxyInvocationHandler();

        UserService service = new UserServiceImpl();

        pih.setTarget(service);
        UserService proxy = (UserService)pih.getProxy();

        proxy.add();
    }
}
