package com.example.demo.services;

import com.example.demo.generated.types.Review;
import com.example.demo.generated.types.SubmittedReview;
import com.github.javafaker.Faker;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This service emulates a data store.
 * For convenience in the demo we just generate Reviews in memory, but imagine this would be backed by for example a database.
 * If this was indeed backed by a database, it would be very important to avoid the N+1 problem, which means we need to use a DataLoader to call this class.
 */
@Service
public class DefaultReviewsService implements ReviewsService {
    private final static Logger logger = LoggerFactory.getLogger(DefaultReviewsService.class);

    private final ShowsService showsService;
    private final Map<Integer, List<Review>> reviews = new ConcurrentHashMap<>();
    private FluxSink<Review> reviewsStream;
    private ConnectableFlux<Review> reviewsPublisher;

    public DefaultReviewsService(ShowsService showsService) {
        this.showsService = showsService;
    }

    @PostConstruct
    private void createReviews() {
        Faker faker = new Faker();

        //For each show we generate a random set of reviews.
        showsService.shows().forEach(show -> {
            List<Review> generatedReviews = IntStream.range(0, faker.number().numberBetween(1, 20)).mapToObj(number -> {
                LocalDateTime date = faker.date().past(300, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                return Review.newBuilder().submittedDate(OffsetDateTime.of(date, ZoneOffset.UTC)).username(faker.name().username()).starScore(faker.number().numberBetween(0, 6)).build();
            }).collect(Collectors.toList());

            reviews.put(show.getId(), generatedReviews);
        });


        Flux<Review> publisher = Flux.create(emitter -> {
            reviewsStream = emitter;
        });

        reviewsPublisher = publisher.publish();
        reviewsPublisher.connect();
    }

    /**
     * Hopefully nobody calls this for multiple shows within a single query, that would indicate the N+1 problem!
     */
    public List<Review> reviewsForShow(Integer showId) {
        return reviews.get(showId);
    }

    /**
     * This is the method we want to call when loading reviews for multiple shows.
     * If this code was backed by a relational database, it would select reviews for all requested shows in a single SQL query.
     */
    public Map<Integer, List<Review>> reviewsForShows(List<Integer> showIds) {
        logger.info("Loading reviews for shows {}", showIds.stream().map(String::valueOf).collect(Collectors.joining(", ")));

        return reviews
                .entrySet()
                .stream()
                .filter(entry -> showIds.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void saveReview(SubmittedReview reviewInput) {
        List<Review> reviewsForShow = reviews.computeIfAbsent(reviewInput.getShowId(), (key) -> new ArrayList<>());
        Review review = Review.newBuilder()
                .username(reviewInput.getUsername())
                .starScore(reviewInput.getStarScore())
                .submittedDate(OffsetDateTime.now()).build();

        reviewsForShow.add(review);
        reviewsStream.next(review);

        logger.info("Review added {}", review);
    }

    public void saveReviews(List<SubmittedReview> reviewsInput) {
        reviewsInput.forEach(reviewInput -> {
            List<Review> reviewsForShow = reviews.computeIfAbsent(reviewInput.getShowId(), (key) -> new ArrayList<>());
            Review review = Review.newBuilder()
                    .username(reviewInput.getUsername())
                    .starScore(reviewInput.getStarScore())
                    .submittedDate(OffsetDateTime.now()).build();

            reviewsForShow.add(review);
            reviewsStream.next(review);

            logger.info("Review added {}", review);
        });
    }

    public Publisher<Review> getReviewsPublisher() {
        return reviewsPublisher;
    }
}
