package services;

import model.User;
import model.RegularUser;
import model.AdminUser;
import java.util.Arrays;

public class UserService {
    private User[] users;
    private int userCount;

    public UserService() {
        users = new User[5];
        userCount = 0;
        // NO SAMPLE DATA – start empty
    }

    public void addUser(User user) {
        if (userCount == users.length) {
            users = Arrays.copyOf(users, users.length * 2);
        }
        users[userCount++] = user;
    }

    public User[] getAllUsers() {
        return Arrays.copyOf(users, userCount);
    }

    public User findUserById(String userId) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUserId().equals(userId)) {
                return users[i];
            }
        }
        return null;
    }

    public User findUserByName(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name)) {
                return users[i];
            }
        }
        return null;
    }
}