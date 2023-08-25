package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User postUser(@RequestBody User user) {
        userService.userStorage.postUser(user);
        log.info("Добавление пользователя {}", user);
        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User user) {
        userService.userStorage.putUser(user);
        log.info("Обновление пользователя {}", user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получение пользователей {}", userService.userStorage.getUsers());
        return userService.userStorage.getUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable int id) {
        log.info("Получение пользователя {}", userService.userStorage.getUser(id));
        return userService.userStorage.getUser(id);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Добавление друга с идентификатором {}", friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Удаление друга с идентификатором {}", friendId);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Получений друзей пользователем с идентификатором {} : {}", id, userService.getFriends(id));
        return userService.getFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получений общих друзей пользователя {} с пользователем {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }
}
