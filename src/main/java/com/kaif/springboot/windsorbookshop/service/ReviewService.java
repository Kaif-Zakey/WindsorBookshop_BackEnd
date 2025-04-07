package com.kaif.springboot.windsorbookshop.service;

import com.kaif.springboot.windsorbookshop.dto.ReviewDTO;
import com.kaif.springboot.windsorbookshop.entitis.Review;
import com.kaif.springboot.windsorbookshop.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public void submitReview(Review review) {
        review.setApproved(false);
        reviewRepository.save(review);
    }

    // Fetch approved reviews for a specific book
    public List<Review> getApprovedReviewsForBook(int bookId) {
        return reviewRepository.findByBookIdAndApprovedTrue(bookId);
    }

    // Fetch all pending reviews
    public List<ReviewDTO> findPendingReviews() {
        return reviewRepository.findByApprovedFalse()
                .stream()
                .map(ReviewDTO::new) // Convert to DTO
                .collect(Collectors.toList());
    }

    // Approve a review
    public Review approveReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setApproved(true);
        return reviewRepository.save(review);
    }

    // Reject a review
    public boolean rejectReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
       if (review != null) {
           reviewRepository.delete(review);
           return true;
    }else {
           return false;
       }
    }

    public List<ReviewDTO> getApprovedReviews() {
        return reviewRepository.findByApprovedTrue()
                .stream()
                .map(ReviewDTO::new) // Convert to DTO
                .collect(Collectors.toList());
    }
}
