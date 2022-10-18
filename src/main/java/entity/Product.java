package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}

