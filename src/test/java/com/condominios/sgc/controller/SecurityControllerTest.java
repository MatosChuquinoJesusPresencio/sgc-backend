package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

class SecurityControllerTest extends ControllerTestBase {

    private final String TOKEN = JwtTestUtil.accessToken(4L, "agente@test.com", "AGENTE_SEGURIDAD");

    @Test
    void obtenerStatus_returns200() throws Exception {
        mockMvc.perform(get("/api/security/dashboard/status")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void listarSlots_returns200() throws Exception {
        mockMvc.perform(get("/api/security/parking-slots")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void listarPrestamosActivos_returns200() throws Exception {
        mockMvc.perform(get("/api/security/asset-loans/active-carts")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void verificarVehiculo_withUnknownPlate_returns400() throws Exception {
        mockMvc.perform(get("/api/security/vehicles/verify/ABC-123")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isBadRequest());
    }
}
