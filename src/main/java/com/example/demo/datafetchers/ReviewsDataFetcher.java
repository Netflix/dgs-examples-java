package com.example.demo.datafetchers;

import com.example.demo.dataloaders.ReviewsDataLoader;
import com.example.demo.generated.DgsConstants;
import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.Show;
import com.example.demo.generated.types.SubmittedReview;
import com.example.demo.services.DefaultReviewsService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.InputArgument;
import org.dataloader.DataLoader;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


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
    public List<Review> reviews(DgsDataFetchingEnvironment dfe) {
        Show show = dfe.getSource();

        //Load the reviews from the pre-loaded localContext.
        Map<Integer, List<Review>> reviewsForShows = dfe.getLocalContext();
        return reviewsForShows.get(show.getId());
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddReview)
    public List<Review> addReview(@InputArgument("review")SubmittedReview reviewInput) {
        reviewsService.saveReview(reviewInput);

        List<Review> reviews = reviewsService.reviewsForShow(reviewInput.getShowId());

        return Objects.requireNonNullElseGet(reviews, List::of);
    }

    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE, field = DgsConstants.SUBSCRIPTION.ReviewAdded)
    public Publisher<Review> reviewAddedSubscription(@InputArgument("showId") Integer showId) {
        return reviewsService.getReviewsPublisher();
    }
}
