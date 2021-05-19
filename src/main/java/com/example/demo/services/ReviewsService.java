package com.example.demo.services;

import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.SubmittedReview;
import com.example.demo.scalars.DateRange;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Map;

public interface ReviewsService {
    List<Review> reviewsForShow(Integer showId);

    Map<Integer, List<Review>> reviewsForShows(List<Integer> showIds);

    void saveReview(SubmittedReview reviewInput);

    void saveReviews(List<SubmittedReview> reviewsInput);

    Publisher<Review> getReviewsPublisher();

    List<Review> listReviews(DateRange dateRange);
}
