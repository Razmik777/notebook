package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;
import notebook.util.UserValidator;

import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run() {
        Commands com;

        while (true) {
            String command = prompt("Введите команду: ").toUpperCase();
            com = Commands.valueOf(command);
            if (com == Commands.EXIT)
                return;
            switch (com) {
                case CREATE:
                    User u = createUser();
                    userController.saveUser(u);
                    break;
                case READ:
                    String id = prompt("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case READALL:
                    System.out.println(userController.readAll());
                    break;
                case DELETE:
                    String userIdToDelete = prompt("Введите идентификатор пользователя для удаления: ");
                    try {
                        long userId = Long.parseLong(userIdToDelete);
                        userController.delete(userId);
                        System.out.println("Пользователь успешно удален.");
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный идентификатор пользователя.");
                    }
                    break;
                case UPDATE:
                    String userId = prompt("Enter user id: ");
                    userController.updateUser(userId, createUser());
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    public String checkLine(String line) {
        line = line.trim().replace(" ", "");
        if (!line.isEmpty()) {
            return line;
        } else {
            System.out.println("Значение не может быть пустым.\n");
            line = prompt("Попробуйте еще раз: ");
            return checkLine(line);
        }
    }

    private User createUser() {
        String firstName = checkLine(prompt("Имя: "));
        String lastName = checkLine(prompt("Фамилия: "));
        String phone = checkLine(prompt("Номер телефона: "));
        return new User(firstName, lastName, phone);
    }
}
