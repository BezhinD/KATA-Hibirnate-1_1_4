package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Nikita", "Olegovich", (byte) 22);
        userService.saveUser("Olga", "Olegovna", (byte) 23);
        userService.saveUser("Alena", "Petrovna", (byte) 44);
        userService.saveUser("Dmitry", "Igorevich", (byte) 32);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
