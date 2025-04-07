package com.kaif.springboot.windsorbookshop.repo;

import com.kaif.springboot.windsorbookshop.entitis.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookIdAndApprovedTrue(int bookId);
    List<Review> findByApprovedTrue();
    List<Review> findByApprovedFalse();  // Fetch pending reviews

}
