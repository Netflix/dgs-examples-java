package com.example.demo.types;

import com.example.demo.generated.types.Image;
import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.Show;

import java.util.List;

public class InternalShow {

    private final int id;

    public String getTitle() {
        return show.getTitle();
    }

    public void setTitle(String title) {
        show.setTitle(title);
    }

    public Integer getReleaseYear() {
        return show.getReleaseYear();
    }

    public void setReleaseYear(Integer releaseYear) {
        show.setReleaseYear(releaseYear);
    }

    public List<Review> getReviews() {
        return show.getReviews();
    }

    public void setReviews(List<Review> reviews) {
        show.setReviews(reviews);
    }

    public List<Image> getArtwork() {
        return show.getArtwork();
    }

    public void setArtwork(List<Image> artwork) {
        show.setArtwork(artwork);
    }

    public static Show.Builder newBuilder() {
        return Show.newBuilder();
    }

    private final Show show;

    public InternalShow(int id, Show show) {
        this.id = id;
        this.show = show;
    }

    public int getId() {
        return id;
    }
}
