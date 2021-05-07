package com.example.demo.dataloaders;

import com.example.demo.generated.types.Review;
import com.example.demo.services.DefaultReviewsService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.MappedBatchLoader;
import org.dataloader.MappedBatchLoaderWithContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "reviewsWithContext")
public class ReviewsDataLoaderWithContext implements MappedBatchLoaderWithContext<Integer, List<Review>> {
    private final DefaultReviewsService reviewsService;

    @Autowired
    public ReviewsDataLoaderWithContext(DefaultReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @Override
    public CompletionStage<Map<Integer, List<Review>>> load(Set<Integer> keys, BatchLoaderEnvironment environment) {
        return CompletableFuture.supplyAsync(() -> reviewsService.reviewsForShows(new ArrayList<>(keys)));
    }

}
