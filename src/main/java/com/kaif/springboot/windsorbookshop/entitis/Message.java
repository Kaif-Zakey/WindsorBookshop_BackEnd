package com.kaif.springboot.windsorbookshop.entitis;

import com.kaif.springboot.windsorbookshop.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @Column(nullable = false, length = 1000) // Message content
    private String content;

    @Column(nullable = false)
    private LocalDateTime sentAt;



}
