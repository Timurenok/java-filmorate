package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidDateException;
import ru.yandex.practicum.filmorate.exception.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.InvalidLoginException;
import ru.yandex.practicum.filmorate.exception.UnknownUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private static final LocalDate DATE_NOW = LocalDate.now();
    private static int id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User postUser(@RequestBody User user) {
        checkUser(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Добавление пользователя {}", user);
        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User user) {
        checkUser(user);
        if (users.get(user.getId()) == null) {
            throw new UnknownUserException("Пользователя с идентификатором " + user.getId() + " не существует.");
        }
        users.put(user.getId(), user);
        log.info("Обновление пользователя {}", user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    private void checkUser(User user) {
        if (user.getEmail().isBlank() || user.getEmail() == null) {
            throw new InvalidEmailException("Электронная почта не может быть пустой.");
        }
        if (!user.getEmail().contains("@")) {
            throw new InvalidEmailException("Электронная почта должна содержать символ '@'");
        }
        if (user.getLogin().isBlank() || user.getLogin() == null) {
            throw new InvalidLoginException("Логин не может быть пустым.");
        }
        if (user.getLogin().contains(" ")) {
            throw new InvalidLoginException("Логин не может содержать пробелы.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(DATE_NOW)) {
            throw new InvalidDateException("Дата рождения не может быть в будущем.");
        }
        System.out.println(user);
    }

    private int generateId() {
        return id++;
    }
}
