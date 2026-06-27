package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CatalogoControllerTest extends ControllerTestBase {

    private final String TOKEN = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");

    @Order(1)
    @Test
    void listarPaises_returns200() throws Exception {
        mockMvc.perform(get("/api/catalogs/countries")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Perú"))
                .andExpect(jsonPath("$[0].codigoIso").value("PE"));
    }

    @Order(1)
    @Test
    void listarCiudades_returns200() throws Exception {
        mockMvc.perform(get("/api/catalogs/countries/1/cities")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Lima"))
                .andExpect(jsonPath("$[0].idPais").value(1));
    }

    @Order(1)
    @Test
    void listarCiudades_paisSinCiudades_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/catalogs/countries/999/cities")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Order(2)
    @Test
    void endpointSinToken_returns401() throws Exception {
        mockMvc.perform(get("/api/catalogs/countries"))
                .andExpect(status().isUnauthorized());
    }

    @Order(2)
    @Test
    void endpointConRolIncorrecto_returns403() throws Exception {
        var propToken = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");
        mockMvc.perform(get("/api/catalogs/countries")
                        .header("Authorization", "Bearer " + propToken))
                .andExpect(status().isForbidden());
    }
}
