package com.kaif.springboot.windsorbookshop.entitis;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private double price;
    private int quantity;


    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    private String imageName;


    @ManyToOne
    private Authors authors;

    @ManyToOne
    private Category category;

    public String getCategoryName() {
        if (this.category != null) {
            return this.category.getName(); // Assuming 'getName()' is a method in the 'Category' entity
        }
        return "Unknown"; // Default if no category
    }

}
