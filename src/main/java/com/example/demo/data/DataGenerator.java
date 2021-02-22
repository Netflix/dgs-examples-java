package com.example.demo.data;

import com.example.demo.services.ReviewsService;
import com.example.demo.services.ShowsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataGenerator {

    public DataGenerator(ShowsRepository showsRepository, ShowsService showsService, ReviewsService reviewsService, ReviewsRepository reviewsRepository) {
        this.showsRepository = showsRepository;
        this.showsService = showsService;
        this.reviewsService = reviewsService;
        this.reviewsRepository = reviewsRepository;
    }

    private ShowsRepository showsRepository;
    private ShowsService showsService;
    private ReviewsService reviewsService;
    private ReviewsRepository reviewsRepository;

    @PostConstruct
    public void createData() {
        showsService.shows().stream()
                .peek(s -> {
                    s.setReviews(reviewsService.reviewsForShow(s.getId()));
                })
                .map(JpaShow::fromShow)
                .peek(s -> s.setId(null))
                .peek(s -> reviewsRepository.saveAll(s.getReviews()))
                .forEach(showsRepository::save);
    }
}
