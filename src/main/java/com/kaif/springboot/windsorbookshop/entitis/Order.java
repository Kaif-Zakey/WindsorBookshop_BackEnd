package com.kaif.springboot.windsorbookshop.entitis;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kaif.springboot.windsorbookshop.entitis.OrderItem;
import com.kaif.springboot.windsorbookshop.user.OrderStatus;
import com.kaif.springboot.windsorbookshop.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Customer who placed the order

    private double totalPrice;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems;  // Associated order items
}
