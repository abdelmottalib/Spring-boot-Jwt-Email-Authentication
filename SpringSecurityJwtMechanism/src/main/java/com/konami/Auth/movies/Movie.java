package com.konami.Auth.movies;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.konami.Auth.feedback.FeedBack;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties("movie")
    private List<FeedBack> feedbacks;

}

