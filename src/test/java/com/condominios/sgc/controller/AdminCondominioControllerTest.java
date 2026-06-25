package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

class AdminCondominioControllerTest extends ControllerTestBase {

    private final String TOKEN = JwtTestUtil.accessToken(2L, "admin@test.com", "ADMINISTRADOR_CONDOMINIO");

    @Test
    void obtenerMiCondominio_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/condominium/my-info")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Residencial Test"));
    }

    @Test
    void actualizarMiCondominio_returns200() throws Exception {
        mockMvc.perform(put("/api/admin/condominium/my-info")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre": "Residencial Test Updated",
                                    "direccion": "Av. Test 456"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Residencial Test Updated"));
    }

    @Test
    void obtenerEstructura_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/structure")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void listarApartamentos_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/apartments")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numero").value("101"));
    }

    @Test
    void listarActivos_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/assets")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }
}
