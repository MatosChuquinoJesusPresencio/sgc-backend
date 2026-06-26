package com.condominios.sgc.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

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

    @Test
    void logout_withValidToken_returns200AndClearsCookies() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");
        mockMvc.perform(post("/api/auth/logout")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(cookie().maxAge("access_token", 0))
                .andExpect(cookie().maxAge("refresh_token", 0));
    }

    @Test
    void logout_withoutToken_returns401() throws Exception {
        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void me_withValidToken_returnsCurrentUser() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");
        mockMvc.perform(get("/api/auth/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.correo").value("super@test.com"))
                .andExpect(jsonPath("$.rol").value("SUPER_ADMINISTRADOR"));
    }

    @Test
    void me_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void changePassword_withoutToken_returns401() throws Exception {
        mockMvc.perform(put("/api/auth/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "contrasenaActual": "123456",
                                    "nuevaContrasena": "NewPassword1"
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void email_withoutToken_returns401() throws Exception {
        mockMvc.perform(put("/api/auth/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nuevoCorreo": "nuevo@test.com",
                                    "contrasena": "123456"
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }
}
