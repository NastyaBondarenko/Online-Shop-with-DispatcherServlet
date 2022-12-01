package com.bondarenko.onlineshop.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("when Get Login Page then Ok Status Returned")
    @WithAnonymousUser()
    void whenGetLoginPage_thenOkStatusReturned() throws Exception {
        mockMvc.perform(get("/login")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}