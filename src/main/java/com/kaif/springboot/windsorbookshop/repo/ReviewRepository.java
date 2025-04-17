package com.kaif.springboot.windsorbookshop.repo;

import com.kaif.springboot.windsorbookshop.entitis.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookIdAndApprovedTrue(int bookId);
    List<Review> findByApprovedTrue();
    List<Review> findByApprovedFalse();  // Fetch pending reviews

}
