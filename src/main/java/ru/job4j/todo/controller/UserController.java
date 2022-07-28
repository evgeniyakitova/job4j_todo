package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String regUser(User user, HttpSession session, Model model) {
        if (userService.add(user)) {
            session.setAttribute("user", user);
            return "redirect:/items";
        }
        model.addAttribute("message", "Пользователь с такой почтой уже существует");
        return "registration";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(User user, HttpSession session, Model model) {
        Optional<User> userInDB = userService.findByEmailAndPwd(user.getEmail(), user.getPassword());
        if (userInDB.isEmpty()) {
            model.addAttribute("message", "Неверный логин или пароль");
            return "login";
        }
        session.setAttribute("user", userInDB.get());
        return "redirect:/items";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
