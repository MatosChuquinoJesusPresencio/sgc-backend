package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;
import com.jayway.jsonpath.JsonPath;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PropietarioControllerTest extends ControllerTestBase {

    private final String TOKEN = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");

    @Order(1)
    @Test
    void obtenerResumen_returns200() throws Exception {
        mockMvc.perform(get("/api/homeowner/dashboard/summary")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void obtenerDetalleApartamento_returns200() throws Exception {
        mockMvc.perform(get("/api/homeowner/apartment/details")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("101"))
                .andExpect(jsonPath("$.torreNombre").value("Torre A"))
                .andExpect(jsonPath("$.pisoNumero").value(1));
    }

    @Order(1)
    @Test
    void listarInquilinos_returns200() throws Exception {
        mockMvc.perform(get("/api/homeowner/tenants")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void listarVehiculos_returns200() throws Exception {
        mockMvc.perform(get("/api/homeowner/vehicles")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void listarLogs_returns200() throws Exception {
        mockMvc.perform(get("/api/homeowner/logs")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray());
    }

    @Order(1)
    @Test
    void endpointSinToken_returns401() throws Exception {
        mockMvc.perform(get("/api/homeowner/dashboard/summary"))
                .andExpect(status().isUnauthorized());
    }

    @Order(1)
    @Test
    void endpointConRolIncorrecto_returns403() throws Exception {
        var adminToken = JwtTestUtil.accessToken(2L, "admin@test.com", "ADMINISTRADOR_CONDOMINIO");
        mockMvc.perform(get("/api/homeowner/dashboard/summary")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isForbidden());
    }

    @Order(2)
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

    @Order(2)
    @Test
    void crearVehiculo_returns200() throws Exception {
        mockMvc.perform(post("/api/homeowner/vehicles")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "marca": "Toyota",
                                    "color": "Rojo",
                                    "modelo": "Corolla",
                                    "placa": "ABC-123",
                                    "tipo": "AUTO"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Order(2)
    @Test
    void eliminarInquilino_returns204() throws Exception {
        var createResult = mockMvc.perform(post("/api/homeowner/tenants")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "TempInq",
                                    "apellidos": "Delete",
                                    "tipoDocumento": "DNI",
                                    "numeroDocumento": "87654321"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();
        var id = JsonPath.read(createResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(delete("/api/homeowner/tenants/" + id)
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isNoContent());
    }

    @Order(2)
    @Test
    void eliminarVehiculo_returns204() throws Exception {
        var createResult = mockMvc.perform(post("/api/homeowner/vehicles")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "marca": "Nissan",
                                    "color": "Azul",
                                    "modelo": "Sentra",
                                    "placa": "XYZ-789",
                                    "tipo": "AUTO"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();
        var id = JsonPath.read(createResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(delete("/api/homeowner/vehicles/" + id)
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isNoContent());
    }

    @Order(2)
    @Test
    void eliminarInquilinoNotFound_returns400() throws Exception {
        mockMvc.perform(delete("/api/homeowner/tenants/9999")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Order(2)
    @Test
    void eliminarVehiculoNotFound_returns400() throws Exception {
        mockMvc.perform(delete("/api/homeowner/vehicles/9999")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isBadRequest());
    }
}
