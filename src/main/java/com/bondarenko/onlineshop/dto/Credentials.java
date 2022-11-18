package com.bondarenko.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Credentials {
    String login;
    String password;
}