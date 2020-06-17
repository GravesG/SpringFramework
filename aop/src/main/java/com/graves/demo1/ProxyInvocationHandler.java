package com.graves.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Graves
 * @DESCRIPTION
 * @create 2020/6/17
 */
public class ProxyInvocationHandler implements InvocationHandler {

    // 被代理的接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    // 获取代理对象
    public Object getProxy(){
        return Proxy.newProxyInstance(ProxyInvocationHandler.class.getClassLoader(),target.getClass().getInterfaces(),new ProxyInvocationHandler());
    }

    // 执行代理方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target,args);
    }
}
