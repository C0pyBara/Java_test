package ru.edu.penzgtu.lab.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Контроллер, который будет показывать HTML-страницы
@Controller
public class HtmlController {

    // При переходе по адресу /authors будет открываться страница authors.html
    @GetMapping("/authors")
    public String showAuthorsPage() {
        return "authors";  // имя файла без .html
    }

    // При переходе по адресу /movies будет открываться страница movies.html
    @GetMapping("/movies")
    public String showMoviesPage() {
        return "movies";  // имя файла без .html
    }
}