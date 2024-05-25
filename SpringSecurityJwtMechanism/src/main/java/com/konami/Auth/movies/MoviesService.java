package com.konami.Auth.movies;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoviesService {
    private final MoviesRepository moviesRepository;

    public List<Movie> getMovies() {
        return this.moviesRepository.findAll();
    }

    public String addMovie(Movie movie) {
        Movie newMovie = Movie.builder().name(movie.getName()).build();
        this.moviesRepository.save(newMovie);
        return "Movie added successfully";
    }
}
