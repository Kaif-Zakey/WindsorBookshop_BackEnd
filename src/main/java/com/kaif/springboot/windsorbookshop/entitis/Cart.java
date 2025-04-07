package com.kaif.springboot.windsorbookshop.entitis;

import com.kaif.springboot.windsorbookshop.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // User who owns the cart


    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books book; // Book in the cart


    private int quantity; // Total price of items in the cart

    private double totalPrice;  // Store total price of this cart item

    // getters and setters
}
