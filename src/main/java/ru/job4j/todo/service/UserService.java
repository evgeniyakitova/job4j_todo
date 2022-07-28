package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.Optional;

@Service
public class UserService {
    private final UserStore userStore;

    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }

    public Optional<User> findByEmailAndPwd(String email, String password) {
        return userStore.findByEmailAndPwd(email, password);
    }

    public boolean add(User user) {
        return userStore.add(user);
    }
}
