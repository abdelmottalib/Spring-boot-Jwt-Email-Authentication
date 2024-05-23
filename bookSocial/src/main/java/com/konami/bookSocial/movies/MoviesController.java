package com.konami.bookSocial.movies;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviesController {
    @GetMapping("/movies")
    public String getMovies() {
        return "Movies";
    }
}
