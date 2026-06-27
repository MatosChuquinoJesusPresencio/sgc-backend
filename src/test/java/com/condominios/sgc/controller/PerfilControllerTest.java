package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

class PerfilControllerTest extends ControllerTestBase {

    @Test
    void a_obtenerPerfil_withValidToken_returns200() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");

        mockMvc.perform(get("/api/profile")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("Super"))
                .andExpect(jsonPath("$.apellidos").value("Admin"))
                .andExpect(jsonPath("$.correo").value("super@test.com"))
                .andExpect(jsonPath("$.rol").value("SUPER_ADMINISTRADOR"));
    }

    @Test
    void actualizarPerfil_withValidData_returns200() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");

        mockMvc.perform(put("/api/profile")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "SuperUpdated",
                                    "apellidos": "AdminUpdated",
                                    "telefono": "+51999000099"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerPerfil_withAdminToken_returnsCorrectData() throws Exception {
        var token = JwtTestUtil.accessToken(2L, "admin@test.com", "ADMINISTRADOR_CONDOMINIO");

        mockMvc.perform(get("/api/profile")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("Admin"))
                .andExpect(jsonPath("$.apellidos").value("Condominio"))
                .andExpect(jsonPath("$.rol").value("ADMINISTRADOR_CONDOMINIO"));
    }

    @Test
    void obtenerPerfil_withPropietarioToken_returnsCorrectData() throws Exception {
        var token = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");

        mockMvc.perform(get("/api/profile")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("Propietario"))
                .andExpect(jsonPath("$.rol").value("PROPIETARIO"));
    }
}
