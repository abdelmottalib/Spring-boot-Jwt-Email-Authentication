package com.konami.Auth.feedback;

import com.konami.Auth.movies.Movie;
import com.konami.Auth.movies.MoviesRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackService  {

    private final FeedbackRepository feedbackRepository;
    private final MoviesRepository moviesRepository;
    public FeedBack createFeedback(FeedBack feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<FeedBack> getFeedbacks() {
        return feedbackRepository.findAll();
    }

    public FeedBack getFeedback(Integer id) {
        FeedBack feedBack =  feedbackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found"));
        System.out.println("feedBack = " + feedBack);
        Movie movie = moviesRepository.findById(feedBack.getMovie().getId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        feedBack.setMovie(movie);
        return feedBack;
    }
}
