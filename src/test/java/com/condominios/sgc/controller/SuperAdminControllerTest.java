package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
class SuperAdminControllerTest extends ControllerTestBase {

    private final String TOKEN = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");

    @Order(1)
    @Test
    void listarDisponibles_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/administrators/available")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
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

    @Order(1)
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

    @Order(1)
    @Test
    void listarCondominiosDisponibles_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/condominiums/unassigned")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void invalidarSesion_returns200() throws Exception {
        mockMvc.perform(post("/api/super-admin/users/2/invalidate-session")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void listarAdministradores_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/administrators")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").isNumber());
    }

    @Order(1)
    @Test
    void listarCondominios_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/condominiums")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").isNumber());
    }

    @Order(1)
    @Test
    void obtenerMetricasDashboard_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/dashboard/metrics")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCondominios").isNumber())
                .andExpect(jsonPath("$.totalAdministradores").isNumber())
                .andExpect(jsonPath("$.totalPropietarios").isNumber());
    }

    @Order(1)
    @Test
    void obtenerAdminsRecientes_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/dashboard/recent-admins")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void obtenerCondominiosRecientes_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/dashboard/recent-condos")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void listarUsuarios_returns200() throws Exception {
        mockMvc.perform(get("/api/super-admin/users")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").isNumber());
    }

    @Order(1)
    @Test
    void endpointSinToken_returns401() throws Exception {
        mockMvc.perform(get("/api/super-admin/administrators"))
                .andExpect(status().isUnauthorized());
    }

    @Order(1)
    @Test
    void endpointConRolIncorrecto_returns403() throws Exception {
        var propToken = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");
        mockMvc.perform(get("/api/super-admin/administrators")
                        .header("Authorization", "Bearer " + propToken))
                .andExpect(status().isForbidden());
    }

    @Order(2)
    @Test
    void actualizarAdministrador_returns200() throws Exception {
        mockMvc.perform(put("/api/super-admin/administrators/2")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "AdminActualizado",
                                    "apellidos": "Test",
                                    "telefono": "+51999000099"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("AdminActualizado"));
    }

    @Order(2)
    @Test
    void activarDesactivarAdministrador_returns200() throws Exception {
        mockMvc.perform(patch("/api/super-admin/administrators/2")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"activo": false}
                                """))
                .andExpect(status().isOk());
        // Reactivate
        mockMvc.perform(patch("/api/super-admin/administrators/2")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"activo": true}
                                """))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void actualizarCondominio_returns200() throws Exception {
        mockMvc.perform(put("/api/super-admin/condominiums/1")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre": "CondominioActualizado",
                                    "idPais": 1,
                                    "idCiudad": 1,
                                    "direccion": "Av. Actualizada 789"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("CondominioActualizado"));
    }

    @Order(2)
    @Test
    void activarDesactivarCondominio_returns200() throws Exception {
        mockMvc.perform(patch("/api/super-admin/condominiums/1")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"activo": false}
                                """))
                .andExpect(status().isOk());
        // Reactivate
        mockMvc.perform(patch("/api/super-admin/condominiums/1")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"activo": true}
                                """))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void forzarCambioContrasena_returns200() throws Exception {
        mockMvc.perform(put("/api/super-admin/users/2/force-password")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nuevaContrasena": "NewPass1234"}
                                """))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void eliminarAdministrador_returns204() throws Exception {
        var createResult = mockMvc.perform(post("/api/super-admin/administrators")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "Temp",
                                    "apellidos": "Admin",
                                    "correo": "temp@test.com",
                                    "telefono": "+51999000099",
                                    "contrasena": "Password123!"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();
        var id = JsonPath.read(createResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(delete("/api/super-admin/administrators/" + id)
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isNoContent());
    }

    @Order(2)
    @Test
    void eliminarCondominio_returns204() throws Exception {
        var createResult = mockMvc.perform(post("/api/super-admin/condominiums")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre": "Temp Condominio",
                                    "idPais": 1,
                                    "idCiudad": 1,
                                    "direccion": "Temp 123"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();
        var id = JsonPath.read(createResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(delete("/api/super-admin/condominiums/" + id)
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isNoContent());
    }

    @Order(2)
    @Test
    void asignarCondominio_returns200() throws Exception {
        var adminResult = mockMvc.perform(post("/api/super-admin/administrators")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "SinCondo",
                                    "apellidos": "Admin",
                                    "correo": "sincondo@test.com",
                                    "telefono": "+51999000099",
                                    "contrasena": "Password123!"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();
        var adminId = JsonPath.read(adminResult.getResponse().getContentAsString(), "$.id");
        var condoResult = mockMvc.perform(post("/api/super-admin/condominiums")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre": "CondoParaAsignar",
                                    "idPais": 1,
                                    "idCiudad": 1,
                                    "direccion": "Av. Asignar 123"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();
        var condominioId = JsonPath.read(condoResult.getResponse().getContentAsString(), "$.id");

        var assignBody = "{\"idCondominio\": " + condominioId + "}";
        mockMvc.perform(put("/api/super-admin/administrators/" + adminId + "/assign-condo")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(assignBody))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void eliminarAdministradorNotFound_returns400() throws Exception {
        mockMvc.perform(delete("/api/super-admin/administrators/9999")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Order(2)
    @Test
    void eliminarCondominioNotFound_returns400() throws Exception {
        mockMvc.perform(delete("/api/super-admin/condominiums/9999")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isBadRequest());
    }
}
