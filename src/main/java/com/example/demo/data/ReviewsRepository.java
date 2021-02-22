package com.example.demo.data;

import org.springframework.data.repository.CrudRepository;

public interface ReviewsRepository extends CrudRepository<JpaReview, Integer> {
}
