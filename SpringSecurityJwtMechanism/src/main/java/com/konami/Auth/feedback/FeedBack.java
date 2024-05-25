package com.konami.Auth.feedback;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.konami.Auth.movies.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class FeedBack {
    @Id
    @GeneratedValue
    private Integer id;
    private String comment;

   @ManyToOne
   @JsonIgnoreProperties("feedbacks")
   @JoinColumn(name = "movie_id")
   private Movie movie;

}
