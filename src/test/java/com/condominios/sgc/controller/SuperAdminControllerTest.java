package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

class SuperAdminControllerTest extends ControllerTestBase {

    private final String TOKEN = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");

    @Test
    void listarDisponibles_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/administrators/available")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void crearAdministrador_returns201() throws Exception {
        mockMvc.perform(post("/api/super-admin/administrators")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "Nuevo",
                                    "apellidos": "Admin",
                                    "correo": "nuevo@test.com",
                                    "telefono": "+51999000099",
                                    "contrasena": "Password123!"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombres").value("Nuevo"));
    }

    @Test
    void crearCondominio_returns201() throws Exception {
        mockMvc.perform(post("/api/super-admin/condominiums")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre": "Nuevo Condominio",
                                    "idPais": 1,
                                    "idCiudad": 1,
                                    "direccion": "Av. Nueva 456"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombre").value("Nuevo Condominio"));
    }

    @Test
    void listarCondominiosDisponibles_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/condominiums/unassigned")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void invalidarSesion_returns200() throws Exception {
        mockMvc.perform(post("/api/super-admin/users/2/invalidate-session")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }
}
