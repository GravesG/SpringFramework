package com.graves.demo2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Graves
 * @DESCRIPTION
 * @create 2020/6/18
 */
public class Test2 {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserService userService = ctx.getBean("userService", UserService.class);
        userService.add();
    }
}
