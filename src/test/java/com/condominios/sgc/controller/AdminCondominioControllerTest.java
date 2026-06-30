package com.condominios.sgc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
class AdminCondominioControllerTest extends ControllerTestBase {

    private final String TOKEN = JwtTestUtil.accessToken(2L, "admin@test.com", "ADMINISTRADOR_CONDOMINIO");

    @Order(1)
    @Test
    void obtenerMiCondominio_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/condominium/my-info")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").isString())
                .andExpect(jsonPath("$.configuracion.maxAutos").isNumber());
    }

    @Order(1)
    @Test
    void obtenerEstructura_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/structure")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void listarApartamentos_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/apartments?page=0&size=20")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].numero").value("101"))
                .andExpect(jsonPath("$.total").isNumber());
    }

    @Order(1)
    @Test
    void listarActivos_carrito_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/assets?type=CARRITO")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").isNumber());
    }

    @Order(1)
    @Test
    void listarActivos_estacionamiento_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/assets?type=ESTACIONAMIENTO")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").isNumber());
    }

    @Order(1)
    @Test
    void listarActivos_sinType_returns400() throws Exception {
        mockMvc.perform(get("/api/admin/assets")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Order(1)
    @Test
    void obtenerMetricasDashboard_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard/metrics")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPropietarios").isNumber());
    }

    @Order(1)
    @Test
    void listarUsuarios_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/users")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray());
    }

    @Order(1)
    @Test
    void listarLogs_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/logs?type=VEHICULAR")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray());
    }

    @Order(1)
    @Test
    void endpointSinToken_returns401() throws Exception {
        mockMvc.perform(get("/api/admin/apartments"))
                .andExpect(status().isUnauthorized());
    }

    @Order(1)
    @Test
    void endpointConRolIncorrecto_returns403() throws Exception {
        var propToken = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");
        mockMvc.perform(get("/api/admin/apartments")
                        .header("Authorization", "Bearer " + propToken))
                .andExpect(status().isForbidden());
    }

    @Order(1)
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
        mockMvc.perform(put("/api/admin/condominium/my-info")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre": "Residencial Test",
                                    "direccion": "Av. Test 123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Residencial Test"));
    }

    @Order(1)
    @Test
    void actualizarConfiguracion_returns200() throws Exception {
        mockMvc.perform(put("/api/admin/condominium/configuracion")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "maxAutos": 5,
                                    "maxMotos": 3,
                                    "penalizacionPorMin": 0.75,
                                    "maxTiempoPrestamoMin": 60,
                                    "maxEstacionamientosPorDepto": 3,
                                    "maxCarritosPorDepto": 1,
                                    "maxVehiculosPorDepto": 4,
                                    "maxInquilinosPorDepto": 6
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxAutos").value(5))
                .andExpect(jsonPath("$.maxMotos").value(3))
                .andExpect(jsonPath("$.penalizacionPorMin").value(0.75))
                .andExpect(jsonPath("$.maxTiempoPrestamoMin").value(60))
                .andExpect(jsonPath("$.maxEstacionamientosPorDepto").value(3))
                .andExpect(jsonPath("$.maxCarritosPorDepto").value(1))
                .andExpect(jsonPath("$.maxVehiculosPorDepto").value(4))
                .andExpect(jsonPath("$.maxInquilinosPorDepto").value(6));
        // Restore defaults
        mockMvc.perform(put("/api/admin/condominium/configuracion")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "maxAutos": 2,
                                    "maxMotos": 4,
                                    "penalizacionPorMin": 1.0,
                                    "maxTiempoPrestamoMin": 30,
                                    "maxEstacionamientosPorDepto": 2,
                                    "maxCarritosPorDepto": 2,
                                    "maxVehiculosPorDepto": 2,
                                    "maxInquilinosPorDepto": 2
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Order(1)
    @Test
    void obtenerConfiguracion_returns200() throws Exception {
        mockMvc.perform(get("/api/admin/condominium/configuracion")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxAutos").isNumber())
                .andExpect(jsonPath("$.maxMotos").isNumber())
                .andExpect(jsonPath("$.penalizacionPorMin").isNumber())
                .andExpect(jsonPath("$.maxTiempoPrestamoMin").isNumber())
                .andExpect(jsonPath("$.maxEstacionamientosPorDepto").isNumber())
                .andExpect(jsonPath("$.maxCarritosPorDepto").isNumber())
                .andExpect(jsonPath("$.maxVehiculosPorDepto").isNumber())
                .andExpect(jsonPath("$.maxInquilinosPorDepto").isNumber());
    }

    @Order(2)
    @Test
    void crearNodo_returns200() throws Exception {
        mockMvc.perform(post("/api/admin/structure/nodes")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "tipo": "APARTAMENTO",
                                    "nombreTorre": "Torre A",
                                    "numeroPiso": 1,
                                    "numeroApartamento": 999,
                                    "metraje": 50.0
                                }
                                """))
                .andExpect(status().isOk());
    }

    @SuppressWarnings("unchecked")
    @Order(2)
    @Test
    void eliminarNodo_returns204() throws Exception {
        mockMvc.perform(post("/api/admin/structure/nodes")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "tipo": "APARTAMENTO",
                                    "nombreTorre": "Torre A",
                                    "numeroPiso": 1,
                                    "numeroApartamento": 998,
                                    "metraje": 50.0
                                }
                                """))
                .andExpect(status().isOk());
        var listResult = mockMvc.perform(get("/api/admin/apartments?page=0&size=100")
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andReturn();
        var json = listResult.getResponse().getContentAsString();
        var doc = com.jayway.jsonpath.JsonPath.parse(json);
        var apts = doc.read("$.items[?(@.numero==998)]", java.util.List.class);
        if (!apts.isEmpty()) {
            var aptMap = (java.util.Map<String, Object>) apts.get(0);
            var aptId = ((Number) aptMap.get("id")).longValue();
            mockMvc.perform(delete("/api/admin/structure/nodes/" + aptId)
                            .header("Authorization", "Bearer " + TOKEN)
                            .param("type", "APARTAMENTO"))
                    .andExpect(status().isNoContent());
        }
    }

    @Order(2)
    @Test
    void crearUsuario_returns200() throws Exception {
        mockMvc.perform(post("/api/admin/users")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "Nuevo",
                                    "apellidos": "Agente",
                                    "correo": "nuevoagente@test.com",
                                    "telefono": "+51999000099",
                                    "contrasena": "Password123!",
                                    "rol": "AGENTE_SEGURIDAD"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Order(2)
    @Test
    void actualizarUsuario_returns200() throws Exception {
        var createResult = mockMvc.perform(post("/api/admin/users")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "Temp",
                                    "apellidos": "User",
                                    "correo": "tempuser@test.com",
                                    "telefono": "+51999000099",
                                    "contrasena": "Password123!",
                                    "rol": "AGENTE_SEGURIDAD"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();
        var userId = JsonPath.read(createResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(put("/api/admin/users/" + userId)
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombres": "Updated",
                                    "apellidos": "Name",
                                    "telefono": "+51999000098"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("Updated"));
    }

    @Order(2)
    @Test
    void asignarPropietario_yaAsignado_returns400() throws Exception {
        // Apartment 1 already has propietario 3 assigned
        mockMvc.perform(put("/api/admin/apartments/1/assign-owner")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idPropietario": 3}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Order(2)
    @Test
    void actualizarOcupantes_returns200() throws Exception {
        mockMvc.perform(put("/api/admin/apartments/1/occupants")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "inquilinos": [
                                        {
                                            "nombres": "InquilinoTemp",
                                            "apellidos": "Test",
                                            "tipoDocumento": "DNI",
                                            "numeroDocumento": "87654321"
                                        }
                                    ]
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void crearActivo_returns200() throws Exception {
        mockMvc.perform(post("/api/admin/assets")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "tipo": "ESTACIONAMIENTO",
                                    "codigo": "E-01",
                                    "numero": 1
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Order(2)
    @Test
    void actualizarStatusActivo_returns200() throws Exception {
        // First create an asset
        var createResult = mockMvc.perform(post("/api/admin/assets")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "tipo": "ESTACIONAMIENTO",
                                    "codigo": "E-02",
                                    "numero": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();
        var assetId = JsonPath.read(createResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(put("/api/admin/assets/" + assetId + "/status")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "tipo": "ESTACIONAMIENTO",
                                    "estado": "MANTENIMIENTO",
                                    "disponible": false
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Order(2)
    @Test
    void activarDesactivarUsuario_returns200() throws Exception {
        mockMvc.perform(patch("/api/admin/users/3/status")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"activo": false}
                                """))
                .andExpect(status().isOk());
        mockMvc.perform(patch("/api/admin/users/3/status")
                        .header("Authorization", "Bearer " + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"activo": true}
                                """))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void activarDesactivarUsuario_unauthorized_returns401() throws Exception {
        mockMvc.perform(patch("/api/admin/users/3/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"activo": false}
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Order(2)
    @Test
    void activarDesactivarUsuario_forbidden_returns403() throws Exception {
        var agentToken = JwtTestUtil.accessToken(4L, "agente@test.com", "AGENTE_SEGURIDAD");
        mockMvc.perform(patch("/api/admin/users/3/status")
                        .header("Authorization", "Bearer " + agentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"activo": false}
                                """))
                .andExpect(status().isForbidden());
    }
}
