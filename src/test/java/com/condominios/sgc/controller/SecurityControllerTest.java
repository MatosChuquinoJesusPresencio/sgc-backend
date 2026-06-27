package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SecurityControllerTest extends ControllerTestBase {

    private final String TOKEN = JwtTestUtil.accessToken(4L, "agente@test.com", "AGENTE_SEGURIDAD");

    @Order(1)
    @Test
    void obtenerStatus_returns200() throws Exception {
        mockMvc.perform(get("/api/security/dashboard/status")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void listarSlots_returns200() throws Exception {
        mockMvc.perform(get("/api/security/parking-slots")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void listarPrestamosActivos_returns200() throws Exception {
        mockMvc.perform(get("/api/security/asset-loans/active-carts")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void endpointSinToken_returns401() throws Exception {
        mockMvc.perform(get("/api/security/dashboard/status"))
                .andExpect(status().isUnauthorized());
    }

    @Order(1)
    @Test
    void endpointConRolIncorrecto_returns403() throws Exception {
        var propToken = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");
        mockMvc.perform(get("/api/security/dashboard/status")
                        .header("Authorization", "Bearer " + propToken))
                .andExpect(status().isForbidden());
    }

    @Order(1)
    @Test
    void verificarVehiculo_withUnknownPlate_returns400() throws Exception {
        mockMvc.perform(get("/api/security/vehicles/verify/ABC-123")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Order(1)
    @Test
    void verificarVehiculo_withKnownPlate_returns200() throws Exception {
        var propToken = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");
        mockMvc.perform(post("/api/homeowner/vehicles")
                        .header("Authorization", "Bearer " + propToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "marca": "Toyota",
                                    "color": "Blanco",
                                    "modelo": "Yaris",
                                    "placa": "XYZ-456",
                                    "tipo": "AUTO"
                                }
                                """))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/security/vehicles/verify/XYZ-456")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void registrarPrestamo_withNonExistentCart_returns400() throws Exception {
        mockMvc.perform(post("/api/security/asset-loans")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "codigoCarrito": "NO-EXISTE",
                                    "numeroApartamento": 101,
                                    "nombreSolicitante": "Test",
                                    "dniSolicitante": "12345678"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Order(2)
    @Test
    void registrarDevolucion_withNonExistentLoan_returns400() throws Exception {
        mockMvc.perform(put("/api/security/asset-loans/9999/return")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Order(2)
    @Test
    void registrarEntrada_withUnknownPlate_returns400() throws Exception {
        mockMvc.perform(post("/api/security/access-logs/entry")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "placa": "ZZZ-999",
                                    "metodo": "MANUAL"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Order(2)
    @Test
    void registrarSalida_withNonExistentLogId_returns400() throws Exception {
        mockMvc.perform(put("/api/security/access-logs/exit")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idLogAcceso": 9999}
                                """))
                .andExpect(status().isBadRequest());
    }
}
