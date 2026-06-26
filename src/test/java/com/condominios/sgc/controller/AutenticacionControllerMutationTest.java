package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

class AutenticacionControllerMutationTest extends ControllerTestBase {

    @Test
    void changePassword_changesPasswordSuccessfully() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");
        // Change password
        mockMvc.perform(put("/api/auth/change-password")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "contrasenaActual": "123456",
                                    "nuevaContrasena": "NewPassword1"
                                }
                                """))
                .andExpect(status().isOk());
        // Verify new password works for login
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "super@test.com",
                                    "contrasena": "NewPassword1"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void changePassword_withWrongCurrentPassword_returns401() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");
        mockMvc.perform(put("/api/auth/change-password")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "contrasenaActual": "wrong-password",
                                    "nuevaContrasena": "NewPassword1"
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void email_withValidRequest_returns200() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");
        mockMvc.perform(put("/api/auth/email")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nuevoCorreo": "nuevo@test.com",
                                    "contrasena": "123456"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void email_withWrongPassword_returns401() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");
        mockMvc.perform(put("/api/auth/email")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nuevoCorreo": "nuevo@test.com",
                                    "contrasena": "wrong-password"
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }
}
