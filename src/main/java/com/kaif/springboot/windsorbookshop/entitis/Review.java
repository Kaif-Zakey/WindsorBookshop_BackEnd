package com.kaif.springboot.windsorbookshop.entitis;

import com.kaif.springboot.windsorbookshop.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books book;  // Book being reviewed

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // User who wrote the review

    private Integer rating;  // Rating (1-5)


    private String comment;  // Review comment

    private boolean approved = false;

    private LocalDateTime date = LocalDateTime.now();  // Date when review was posted



    // Getters and Setters
}
