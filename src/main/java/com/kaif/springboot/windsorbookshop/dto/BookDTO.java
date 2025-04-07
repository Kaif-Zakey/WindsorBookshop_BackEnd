package com.kaif.springboot.windsorbookshop.dto;

import com.kaif.springboot.windsorbookshop.entitis.Authors;
import com.kaif.springboot.windsorbookshop.entitis.Category;
import jakarta.persistence.Lob;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookDTO {
    private int id;
    private String title;
    private double price;
    private int quantity;
    private String imageName;
    private String categoryName;
}
