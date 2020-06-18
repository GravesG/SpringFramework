package com.graves.demo2;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author Graves
 * @DESCRIPTION
 * @create 2020/6/18
 */
public class LogAfter implements AfterReturningAdvice {
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("方法后增强");
    }
}
