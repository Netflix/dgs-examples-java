package com.example.demo;

import com.example.demo.datafetchers.ReviewsDataFetcher;
import com.example.demo.datafetchers.ShowsDatafetcher;
import com.example.demo.dataloaders.ReviewsDataLoader;
import com.example.demo.dataloaders.ReviewsDataLoaderWithContext;
import com.example.demo.generated.client.ReviewsGraphQLQuery;
import com.example.demo.generated.client.ReviewsProjectionRoot;
import com.example.demo.scalars.DateRange;
import com.example.demo.scalars.DateRangeScalar;
import com.example.demo.scalars.DateTimeScalar;
import com.example.demo.services.DefaultReviewsService;
import com.example.demo.services.ShowsServiceImpl;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import graphql.ExecutionResult;
import graphql.schema.Coercing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {DgsAutoConfiguration.class, ReviewsDataLoaderWithContext.class, ShowsDatafetcher.class, ReviewsDataFetcher.class, ReviewsDataLoader.class, DateTimeScalar.class, DateRangeScalar.class, DefaultReviewsService.class, ShowsServiceImpl.class})
public class ReviewsDataFetcherTest {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    public void testReviewsWithDateRange() {
        Map<Class<?>, Coercing<?, ?>> scalars = new HashMap<>();
        scalars.put(DateRange.class, new DateRangeScalar());

        GraphQLQueryRequest request = new GraphQLQueryRequest(
                ReviewsGraphQLQuery.newRequest().dateRange(new DateRange(LocalDate.of(2020, 1, 1), LocalDate.now())).build(),
                new ReviewsProjectionRoot().submittedDate().starScore(), scalars);

        ExecutionResult execute = dgsQueryExecutor.execute(request.serialize());
        assertThat(execute.isDataPresent()).isTrue();
    }
}
