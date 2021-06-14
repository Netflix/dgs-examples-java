package com.example.demo;

import com.example.demo.datafetchers.ReviewsDataFetcher;
import com.example.demo.generated.client.AddReviewGraphQLQuery;
import com.example.demo.generated.client.AddReviewProjectionRoot;
import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.SubmittedReview;
import com.example.demo.scalars.DateTimeScalar;
import com.example.demo.services.DefaultReviewsService;
import com.example.demo.services.ShowsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import graphql.ExecutionResult;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test the review added subscription.
 * The subscription query returns a Publisher<ExecutionResult>.
 * Each time a review is added, a new ExecutionResult is given to subscriber.
 * Normally, this publisher is consumed by the Websocket/SSE subscription handler and you don't deal with this code directly, but for testing purposes it's useful to use the stream directly.
 */
@SpringBootTest(classes = {DefaultReviewsService.class, ReviewsDataFetcher.class, DgsAutoConfiguration.class, DateTimeScalar.class})
public class ReviewSubscriptionTest {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    ShowsService showsService;

    @Test
    void reviewSubscription() {
        ExecutionResult executionResult = dgsQueryExecutor.execute("subscription { reviewAdded(showId: 1) {starScore} }");
        Publisher<ExecutionResult> reviewPublisher = executionResult.getData();
        List<Review> reviews = new CopyOnWriteArrayList<>();

        reviewPublisher.subscribe(new Subscriber<ExecutionResult>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(2);
            }

            @Override
            public void onNext(ExecutionResult executionResult) {
                if (executionResult.getErrors().size() > 0) {
                    System.out.println(executionResult.getErrors());
                }
                Map<String, Object> review = executionResult.getData();
                reviews.add(new ObjectMapper().convertValue(review.get("reviewAdded"), Review.class));
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });

        addReview();
        addReview();

        assertThat(reviews.size()).isEqualTo(2);
    }

    private void addReview() {
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                AddReviewGraphQLQuery.newRequest()
                        .review(
                                SubmittedReview.newBuilder()
                                        .showId(1)
                                        .username("testuser")
                                        .starScore(5).build())
                        .build(),
                new AddReviewProjectionRoot()
                        .username()
                        .starScore());
        dgsQueryExecutor.execute(graphQLQueryRequest.serialize());
    }
}
