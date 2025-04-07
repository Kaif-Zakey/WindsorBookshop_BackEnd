package com.kaif.springboot.windsorbookshop.dto;

import com.kaif.springboot.windsorbookshop.entitis.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private int bookId; // Book ID
    private String bookTitle;
    private String userName;
    private Integer rating;
    private String comment;
    private boolean approved;
    private LocalDateTime date;

    // Constructor
    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.bookId = review.getBook().getId(); // Get book ID
        this.bookTitle = review.getBook().getTitle();
        this.userName = review.getUser().getUsername();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.approved = review.isApproved();
        this.date = review.getDate();
    }
}
