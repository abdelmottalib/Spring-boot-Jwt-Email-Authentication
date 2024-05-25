package com.konami.Auth.movies;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.util.List;

@RestController
public class MoviesController {
    private final MoviesService moviesService;
    MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }
    @GetMapping("/movies")
public List<Movie> getMovies() {
        return this.moviesService.getMovies();
    }
    @PostMapping("/movies")
    public String postMovies(@RequestBody Movie movie) {
        System.out.println("the movie is: " + movie);
        return this.moviesService.addMovie(movie);
    }
}
