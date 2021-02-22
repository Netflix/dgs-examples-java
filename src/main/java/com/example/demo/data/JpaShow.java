package com.example.demo.data;

import com.example.demo.generated.types.Show;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class JpaShow {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private Integer releaseYear;

    @OneToMany
    private List<JpaReview> reviews;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<JpaReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<JpaReview> reviews) {
        this.reviews = reviews;
    }



    public static JpaShow fromShow(Show show) {
        JpaShow jpaShow = new JpaShow();
        jpaShow.setTitle(show.getTitle());
        if(show.getReviews() != null) {
            jpaShow.setReviews(show.getReviews().stream().map(JpaReview::fromReview).collect(Collectors.toList()));
        }
        jpaShow.setReleaseYear(show.getReleaseYear());
        return jpaShow;
    }
}
