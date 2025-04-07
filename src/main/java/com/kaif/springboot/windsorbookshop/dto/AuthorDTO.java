package com.kaif.springboot.windsorbookshop.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthorDTO {
    private int id;
    private String name;
    private  String bio;
    private String country;
}
