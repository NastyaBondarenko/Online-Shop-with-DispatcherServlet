package com.bondarenko.onlineshop.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}

