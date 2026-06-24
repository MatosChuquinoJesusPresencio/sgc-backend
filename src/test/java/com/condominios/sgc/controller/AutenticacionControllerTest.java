package com.condominios.sgc.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.condominios.sgc.config.ControllerTestBase;

class AutenticacionControllerTest extends ControllerTestBase {

    @Test
    void login_withValidCredentials_returns200AndCookies() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "super@test.com",
                                    "contrasena": "123456"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("access_token"))
                .andExpect(cookie().exists("refresh_token"))
                .andExpect(cookie().httpOnly("access_token", true))
                .andExpect(cookie().path("access_token", "/"))
                .andExpect(jsonPath("$.usuario.correo").value("super@test.com"))
                .andExpect(jsonPath("$.usuario.rol").value("SUPER_ADMINISTRADOR"))
                .andExpect(jsonPath("$.usuario.id").isNumber())
                .andExpect(jsonPath("$.expiracionAccesoMs").isNumber());
    }

    @Test
    void login_withInvalidPassword_returns401() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "super@test.com",
                                    "contrasena": "wrong-password"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("correo o contraseña incorrectos"))
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void login_withInactiveUser_returns400() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "inactivo@test.com",
                                    "contrasena": "123456"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("el usuario está desactivado"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void login_withMissingFields_returns400() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(containsString("correo")))
                .andExpect(jsonPath("$.error").value(containsString("contrasena")))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void forgotPassword_withValidEmail_returns200() throws Exception {
        mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "super@test.com"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void forgotPassword_withInvalidEmail_returns401() throws Exception {
        mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "nonexistent@test.com"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("correo o contraseña incorrectos"))
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void resetPassword_withInvalidToken_returns401() throws Exception {
        mockMvc.perform(post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "token": "invalid-token",
                                    "nuevaContrasena": "NewPass123!"
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void verifyEmail_withInvalidToken_returns401() throws Exception {
        mockMvc.perform(post("/api/auth/verify-email")
                        .param("token", "invalid-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void refresh_withMissingCookie_returns401() throws Exception {
        mockMvc.perform(post("/api/auth/refresh"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void refresh_withInvalidTokenCookie_returns401() throws Exception {
        mockMvc.perform(post("/api/auth/refresh")
                        .header("Cookie", "refresh_token=invalid-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_thenRefresh_returnsNewTokens() throws Exception {
        var loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "super@test.com",
                                    "contrasena": "123456"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("refresh_token"))
                .andReturn();

        var refreshCookie = loginResult.getResponse().getCookie("refresh_token");

        mockMvc.perform(post("/api/auth/refresh")
                        .cookie(refreshCookie))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("access_token"))
                .andExpect(cookie().exists("refresh_token"))
                .andExpect(jsonPath("$.usuario.correo").value("super@test.com"));
    }

    @Test
    void login_withRememberMe_returns200() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "super@test.com",
                                    "contrasena": "123456",
                                    "recuerdame": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("access_token"))
                .andExpect(cookie().exists("refresh_token"))
                .andExpect(jsonPath("$.usuario.correo").value("super@test.com"));
    }

    @Test
    void login_withAdminCondominio_returnsAdminRol() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "admin@test.com",
                                    "contrasena": "123456"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.rol").value("ADMINISTRADOR_CONDOMINIO"))
                .andExpect(jsonPath("$.usuario.idCondominio").value(1));
    }

    @Test
    void login_withPropietario_returnsPropietarioRol() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "propietario@test.com",
                                    "contrasena": "123456"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.rol").value("PROPIETARIO"));
    }

    @Test
    void login_withAgenteSeguridad_returnsAgenteRol() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "correo": "agente@test.com",
                                    "contrasena": "123456"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.rol").value("AGENTE_SEGURIDAD"));
    }
}
