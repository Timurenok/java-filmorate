package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@lombok.Data
public class User {
    private int id;
    @Email
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
