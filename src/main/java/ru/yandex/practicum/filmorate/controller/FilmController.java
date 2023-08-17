package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private static final int MAX_LENGTH = 200;
    private static final LocalDate EARLIEST_DATE = LocalDate.of(1895, 12, 28);
    private static int id = 1;
    private final HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film postFilm(@RequestBody Film film) {
        checkFilm(film);
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Добавление фильма {}", film);
        return film;
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) {
        checkFilm(film);
        if (films.get(film.getId()) == null) {
            throw new UnknownFilmException("Пользователя с идентификатором " + film.getId() + " не существует.");
        }
        films.put(film.getId(), film);
        log.info("Обновление фильма {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    private void checkFilm(Film film) {
        if (film.getName().isBlank() || film.getName() == null) {
            throw new InvalidNameException("Название не может быть пустым.");
        }
        if (film.getDescription().length() > MAX_LENGTH) {
            throw new InvalidSizeException("Максимальная длина описания — 200 символов.");
        }
        if (film.getReleaseDate().isBefore(EARLIEST_DATE)) {
            throw new InvalidDateException("Дата релиза дожна быть не раньше 28 декабря 1895 года.");
        }
        if (film.getDuration() < 0) {
            throw new InvalidDurationException("Продолжительность фильма должна быть положительной.");
        }
    }

    private int generateId() {
        return id++;
    }
}
