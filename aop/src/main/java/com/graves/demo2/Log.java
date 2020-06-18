package com.graves.demo2;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author Graves
 * @DESCRIPTION
 * @create 2020/6/18
 */
public class Log implements MethodBeforeAdvice {

    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("方法前增强");
    }
}
