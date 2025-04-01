package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import java.util.List;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        System.out.println("Таблица создана");

        userService.saveUser("Name1", "LastName1", (byte) 20);
        System.out.println("User с именем Name1 добавлен в базу данных");

        userService.saveUser("Name2", "LastName2", (byte) 25);
        System.out.println("User с именем Name2 добавлен в базу данных");

        userService.saveUser("Name3", "LastName3", (byte) 31);
        System.out.println("User с именем Name3 добавлен в базу данных");

        userService.saveUser("Name4", "LastName4", (byte) 38);
        System.out.println("User с именем Name4 добавлен в базу данных");

        List<User> usersList = userService.getAllUsers();
        usersList.forEach(System.out::println);

        userService.cleanUsersTable();
        System.out.println("Таблица очищена");

        userService.dropUsersTable();
        System.out.println("Таблица удалена");

    }
}





