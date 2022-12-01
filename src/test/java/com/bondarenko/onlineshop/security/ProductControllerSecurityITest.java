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
    @DisplayName("when Get Delete Product Page with Admin Role then Ok Status Returned")
    void whenGetDeleteProductPage_withAdminRole_thenOkStatusReturned() throws Exception {
        mockMvc.perform(get("/products/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("when Get Delete Product Page with User Role then Is Forbidden Returned")
    void whenGetDeleteProductPage_withUserRole_thenIsForbiddenReturned() throws Exception {
        mockMvc.perform(get("/products/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("when Get Add Product Page with Admin Role then Ok Status Returned")
    void whenGetAddProductPage_withAdminRole_thenOkStatusReturned() throws Exception {
        mockMvc.perform(get("/products/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("when Get Add Product Page with User Role then Is Forbidden Returned")
    void whenGetAddProductPage_withUserRole_thenIsForbiddenReturned() throws Exception {
        mockMvc.perform(get("/products/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "admin:update")
    @DisplayName("when Get Update Product Page with Admin Authorities then Ok Status Returned")
    void whenGetUpdateProductPage_withAdminAuthorities_thenOkStatusReturned() throws Exception {
        mockMvc.perform(get("/products/update")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:update")
    @DisplayName("when Get Update Product Page with User Authorities then Is Forbidden Returned")
    void whenGetUpdateProductPage_withUserAuthorities_thenIsForbiddenReturned() throws Exception {
        mockMvc.perform(get("/products/update")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("when Get All Products with Admin Role then Ok Status Returned")
    void whenGetAllProducts_withAdminRole_thenOkStatusReturned() throws Exception {
        mockMvc.perform(get("/*")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("when Get All Products with User Role then Ok Status Returned")
    void whenGetAllProducts_withUserRole_thenOkStatusReturned() throws Exception {
        mockMvc.perform(get("/*")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "INCORRECT_USER")
    @DisplayName("when Get All Products with Incorrect Role then Is Forbidden Return")
    void whenGetAllProducts_withIncorrectRole_thenIsForbiddenReturn() throws Exception {
        mockMvc.perform(get("/*")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}