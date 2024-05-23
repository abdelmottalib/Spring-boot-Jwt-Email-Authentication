package com.konami.Auth.movies;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviesController {
    @GetMapping("/movies")
    public String getMovies() {
        return "You have successfully accessed the movies endpoint!";
    }
}
