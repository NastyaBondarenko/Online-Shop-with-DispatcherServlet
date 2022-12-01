package com.bondarenko.onlineshop.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerSecurityITest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("when Get All Products and Authenticated And Authorized Then Ok Status Returned")
    void whenGetAllProducts_andAuthenticatedAndAuthorized_ThenOkStatusReturned() throws Exception {
        mockMvc.perform(get("/*")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("when Get All Products with Role User Then Ok Status Returned")
    void whenGetAllProducts_withRoleUser_ThenOkStatusReturned() throws Exception {
        mockMvc.perform(get("/*")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "INCORRECT_USER")
    @DisplayName("when Get All Products with Incorrect Role then Is Forbidden Return")
    void whenGetAllProducts_2ithIncorrectRole_thenIsForbiddenReturn() throws Exception {
        mockMvc.perform(get("/*")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}