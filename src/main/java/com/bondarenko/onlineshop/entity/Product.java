package com.bondarenko.onlineshop.entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
@Builder
@NoArgsConstructor
public class Product {
    @NonNull
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}

