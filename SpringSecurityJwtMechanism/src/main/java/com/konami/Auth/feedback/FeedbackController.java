package com.konami.Auth.feedback;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedBack> createFeedback(@Valid @RequestBody FeedBack feedback) {
        FeedBack createdFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }
    @GetMapping
    public ResponseEntity<List<FeedBack>> getFeedbacks() {
        return ResponseEntity.ok(feedbackService.getFeedbacks());
    }
    @GetMapping("/{id}")
    public ResponseEntity<FeedBack> getFeedback(@PathVariable Integer id) {
        System.out.println("id = " + id);
        return ResponseEntity.ok(feedbackService.getFeedback(id));
    }
}
