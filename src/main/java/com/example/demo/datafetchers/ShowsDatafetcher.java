package com.example.demo.datafetchers;

import com.example.demo.generated.DgsConstants;
import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.Show;
import com.example.demo.services.DefaultReviewsService;
import com.example.demo.services.ShowsService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DgsComponent
public class ShowsDatafetcher {
    private final ShowsService showsService;
    private final DefaultReviewsService reviewsService;

    public ShowsDatafetcher(ShowsService showsService, DefaultReviewsService reviewsService) {
        this.showsService = showsService;
        this.reviewsService = reviewsService;
    }

    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Shows)
    public DataFetcherResult<List<Show>> shows(DataFetchingEnvironment dfe) {

        List<Show> shows = showsService.shows();
        if (dfe.getSelectionSet().contains("reviews")) {

            Map<Integer, List<Review>> reviewsForShows = reviewsService.reviewsForShows(shows.stream().map(Show::getId).collect(Collectors.toList()));

            return DataFetcherResult.<List<Show>>newResult()
                    .data(shows)
                    .localContext(reviewsForShows)
                    .build();
        } else {
            return DataFetcherResult.<List<Show>>newResult().data(shows).build();
        }
    }
}
