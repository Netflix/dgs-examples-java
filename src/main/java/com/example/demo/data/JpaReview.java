package com.example.demo.data;

import com.example.demo.generated.types.Review;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;

@Entity
public class JpaReview{
   @Id @GeneratedValue(strategy = GenerationType.AUTO)
   Integer id;

    private String username;

    private Integer starScore;

    private OffsetDateTime submittedDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStarScore() {
        return starScore;
    }

    public void setStarScore(Integer starScore) {
        this.starScore = starScore;
    }

    public OffsetDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(OffsetDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }

    public static JpaReview fromReview(Review review) {
        JpaReview jpaReview = new JpaReview();
        jpaReview.setStarScore(review.getStarScore());
        jpaReview.setSubmittedDate(review.getSubmittedDate());
        jpaReview.setUsername(review.getUsername());
        return jpaReview;
    }
}
