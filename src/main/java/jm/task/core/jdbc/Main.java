package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;



public class Main {
    public static void main(String[] args) {
        Util.getSessionFactory();
        UserService us = new UserServiceImpl();
        us.createUsersTable();

        us.saveUser("Ivan", "Ivanov", (byte) 27);
        us.saveUser("Alex", "Alexeev", (byte) 14);
        us.saveUser("Vito", "Corleone", (byte) 55);
        us.saveUser("Crocodile", "Gena", (byte) 2);

        for (User user : us.getAllUsers()) {
            System.out.println(user);
        }

        us.cleanUsersTable();

        us.dropUsersTable();
        Util.getSessionFactory().close();


    }
}
