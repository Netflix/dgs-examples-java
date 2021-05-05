package com.example.demo.datafetchers;

import com.example.demo.dataloaders.ReviewsDataLoader;
import com.example.demo.dataloaders.ReviewsDataLoaderWithContext;
import com.example.demo.generated.DgsConstants;
import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.Show;
import com.example.demo.generated.types.SubmittedReview;
import com.example.demo.services.DefaultReviewsService;
import com.netflix.graphql.dgs.*;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@DgsComponent
public class ReviewsDataFetcher {

    private final DefaultReviewsService reviewsService;

    public ReviewsDataFetcher(DefaultReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    /**
     * This datafetcher will be called to resolve the "reviews" field on a Show.
     * It's invoked for each individual Show, so if we would load 10 shows, this method gets called 10 times.
     * To avoid the N+1 problem this datafetcher uses a DataLoader.
     * Although the DataLoader is called for each individual show ID, it will batch up the actual loading to a single method call to the "load" method in the ReviewsDataLoader.
     * For this to work correctly, the datafetcher needs to return a CompletableFuture.
     */
    @DgsData(parentType = DgsConstants.SHOW.TYPE_NAME, field = DgsConstants.SHOW.Reviews)
    public CompletableFuture<List<Review>> reviews(DgsDataFetchingEnvironment dfe) {
        //Instead of loading a DataLoader by name, we can use the DgsDataFetchingEnvironment and pass in the DataLoader classname.
        DataLoader<Integer, List<Review>> reviewsDataLoader = dfe.getDataLoader(ReviewsDataLoaderWithContext.class);

        //Because the reviews field is on Show, the getSource() method will return the Show instance.
        Show show = dfe.getSource();

        //Load the reviews from the DataLoader. This call is async and will be batched by the DataLoader mechanism.
        return reviewsDataLoader.load(show.getId());
    }

    @DgsMutation
    public List<Review> addReview(@InputArgument SubmittedReview review) {
        reviewsService.saveReview(review);

        List<Review> reviews = reviewsService.reviewsForShow(review.getShowId());

        return Optional.ofNullable(reviews).orElse(Collections.emptyList());
    }

    @DgsMutation
    public List<Review> addReviews(@InputArgument(value = "reviews", collectionType = SubmittedReview.class) List<SubmittedReview> reviewsInput) {
        reviewsService.saveReviews(reviewsInput);

        List<Integer> showIds = reviewsInput.stream().map(SubmittedReview::getShowId).collect(Collectors.toList());
        Map<Integer, List<Review>> reviews = reviewsService.reviewsForShows(showIds);

        return reviews.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    @DgsSubscription
    public Publisher<Review> reviewAdded(@InputArgument Integer showId) {
        return reviewsService.getReviewsPublisher();
    }
}
