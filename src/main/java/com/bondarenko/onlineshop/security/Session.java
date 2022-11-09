package com.bondarenko.onlineshop.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
public class Session {
 private String token;
 private LocalDateTime expireDate;
}
