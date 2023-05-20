package com.in28minutes.webservices.restfulwebservices.user.service;

import com.in28minutes.webservices.restfulwebservices.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<User>();
    private static int usersCount = 0;

//    static {
//        users.add(new User(++usersCount, "Maha", LocalDate.now().minusYears(42)));
//        users.add(new User(++usersCount, "Lobna", LocalDate.now().minusYears(40)));
//        users.add(new User(++usersCount, "Ghada", LocalDate.now().minusYears(36)));
//        users.add(new User(++usersCount, "Mohamed", LocalDate.now().minusYears(35)));
//        users.add(new User(++usersCount, "Ahmed", LocalDate.now().minusYears(29)));
//    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);

        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public User save(User user) {
        user.setId(++usersCount);
        users.add(user);

        return user;
    }

    public boolean deleteById(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);

        return users.removeIf(predicate);
    }
}
