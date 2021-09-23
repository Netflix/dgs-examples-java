package com.example.demo;

import com.example.demo.generated.client.AddReviewGraphQLQuery;
import com.example.demo.generated.client.AddReviewProjectionRoot;
import com.example.demo.generated.client.ReviewAddedGraphQLQuery;
import com.example.demo.generated.client.ReviewAddedProjectionRoot;
import com.example.demo.generated.types.SubmittedReview;
import com.netflix.graphql.dgs.client.*;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewSubscriptionIntegrationTest {

    @LocalServerPort
    private Integer port;

    private WebSocketGraphQLClient webSocketGraphQLClient;
    private MonoGraphQLClient graphQLClient;
    private MonoRequestExecutor requestExecutor = (url, headers, body) -> WebClient.create(url)
            .post()
            .bodyValue(body)
            .headers(consumer -> headers.forEach(consumer::addAll))
            .exchangeToMono(r -> r.bodyToMono(String.class).map(responseBody -> new HttpResponse(r.rawStatusCode(), responseBody, r.headers().asHttpHeaders())));


    @BeforeEach
    public void setup() {
        webSocketGraphQLClient = new WebSocketGraphQLClient("ws://localhost:" + port + "/subscriptions", new ReactorNettyWebSocketClient());
        graphQLClient = new DefaultGraphQLClient("http://localhost:" + port + "/graphql");
    }

    @Test
    public void testWebSocketSubscription() {
        GraphQLQueryRequest subscriptionRequest = new GraphQLQueryRequest(
                ReviewAddedGraphQLQuery.newRequest().showId(1).build(),
                new ReviewAddedProjectionRoot().starScore()
        );

        GraphQLQueryRequest addReviewMutation1 = new GraphQLQueryRequest(
                AddReviewGraphQLQuery.newRequest().review(SubmittedReview.newBuilder().showId(1).starScore(5).username("DGS User").build()).build(),
                new AddReviewProjectionRoot().starScore()
        );

        GraphQLQueryRequest addReviewMutation2 = new GraphQLQueryRequest(
                AddReviewGraphQLQuery.newRequest().review(SubmittedReview.newBuilder().showId(1).starScore(3).username("DGS User").build()).build(),
                new AddReviewProjectionRoot().starScore()
        );

        Flux<Integer> starScore = webSocketGraphQLClient.reactiveExecuteQuery(subscriptionRequest.serialize(), Collections.emptyMap()).map(r -> r.extractValue("reviewAdded.starScore"));

        StepVerifier.create(starScore)
                .thenAwait(Duration.ofSeconds(1))
                .then(() -> {
                    graphQLClient.reactiveExecuteQuery(addReviewMutation1.serialize(), Collections.emptyMap(), requestExecutor).block();

                })
                .then(() ->
                        graphQLClient.reactiveExecuteQuery(addReviewMutation2.serialize(), Collections.emptyMap(), requestExecutor).block())
                .expectNext(5)
                .expectNext(3)
                .thenCancel()
                .verify();
    }
}
