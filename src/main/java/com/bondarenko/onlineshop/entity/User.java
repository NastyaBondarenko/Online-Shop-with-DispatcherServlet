package com.bondarenko.onlineshop.entity;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class User {
    private int id;
    private String user;
    private String password;
    private String salt;
}
