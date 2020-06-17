package com.graves.demo1;

import javax.rmi.CORBA.Util;

/**
 * @author Graves
 * @DESCRIPTION
 * @create 2020/6/17
 */
public class UserServiceImpl implements UserService {
    public void add() {
        System.out.println("add");
    }

    public void delete() {
        System.out.println("delete");
    }

    public void update() {
        System.out.println("update");
    }

    public void query() {
        System.out.println("query");
    }
}
