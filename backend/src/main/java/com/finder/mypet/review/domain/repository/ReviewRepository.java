package com.finder.mypet.review.domain.repository;

import com.finder.mypet.review.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
