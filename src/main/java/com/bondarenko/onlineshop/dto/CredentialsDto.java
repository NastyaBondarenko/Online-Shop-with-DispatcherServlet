package com.bondarenko.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CredentialsDto {
    private String login;
    private String password;
}