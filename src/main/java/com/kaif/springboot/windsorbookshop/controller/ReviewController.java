package com.kaif.springboot.windsorbookshop.controller;

import com.kaif.springboot.windsorbookshop.dto.ReviewDTO;
import com.kaif.springboot.windsorbookshop.entitis.Review;
import com.kaif.springboot.windsorbookshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitReview(@RequestBody Review review) {
        try {
            reviewService.submitReview(review); // Submit the review to the service
            return ResponseEntity.ok("Review submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error submitting review: " + e.getMessage());
        }
    }

    @GetMapping("/approved/{bookId}")
    public ResponseEntity<List<Review>> getApprovedReviews(@PathVariable int bookId) {
        List<Review> reviews = reviewService.getApprovedReviewsForBook(bookId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ReviewDTO>> getApprovedReviews() {
        return ResponseEntity.ok(reviewService.getApprovedReviews());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ReviewDTO>> getPendingReviews() {
        return ResponseEntity.ok(reviewService.findPendingReviews());
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Review> approveReview(@PathVariable Long id) {
        Review approvedReview = reviewService.approveReview(id);
        return ResponseEntity.ok(approvedReview);
    }

    @DeleteMapping("/reject/{id}")
    public void  rejectReview(@PathVariable Long id) {
         boolean deleted = reviewService.rejectReview(id);
         if (deleted) {
             System.out.println("Review deleted successfully");
         } else {
             System.out.println("Review not found");
         }
    }
}
