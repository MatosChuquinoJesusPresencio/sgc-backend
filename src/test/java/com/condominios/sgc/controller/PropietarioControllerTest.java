package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

class PropietarioControllerTest extends ControllerTestBase {

    private final String TOKEN = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");

    @Test
    void obtenerResumen_returns200() throws Exception {
        mockMvc.perform(get("/api/homeowner/dashboard/summary")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerDetalleApartamento_returns200() throws Exception {
        mockMvc.perform(get("/api/homeowner/apartment/details")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("101"))
                .andExpect(jsonPath("$.torreNombre").value("Torre A"))
                .andExpect(jsonPath("$.pisoNumero").value(1));
    }

    @Test
    void listarInquilinos_returns200() throws Exception {
        mockMvc.perform(get("/api/homeowner/tenants")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void listarVehiculos_returns200() throws Exception {
        mockMvc.perform(get("/api/homeowner/vehicles")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void crearInquilino_returns200() throws Exception {
        mockMvc.perform(post("/api/homeowner/tenants")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "Inquilino",
                                    "apellidos": "Test",
                                    "tipoDocumento": "DNI",
                                    "numeroDocumento": "12345678"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }
}
