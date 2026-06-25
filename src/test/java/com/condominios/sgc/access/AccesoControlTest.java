package com.condominios.sgc.access;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import com.condominios.sgc.config.ControllerTestBase;
import com.condominios.sgc.config.JwtTestUtil;

class AccesoControlTest extends ControllerTestBase {

    // -- 401: Unauthenticated (no token) --

    @Test
    void profileEndpoint_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void catalogoEndpoint_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/catalogs/countries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminEndpoint_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard/metrics"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void homeownerEndpoint_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/homeowner/dashboard/summary"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void securityEndpoint_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/security/dashboard/status"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void superAdminEndpoint_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/super-admin/administrators"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void logoutEndpoint_withoutToken_returns401() throws Exception {
        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isUnauthorized());
    }

    // -- 403: Wrong role --

    @Test
    void propietario_accessingSuperAdminEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");

        mockMvc.perform(get("/api/super-admin/administrators")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void propietario_accessingAdminEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");

        mockMvc.perform(get("/api/admin/dashboard/metrics")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void propietario_accessingSecurityEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(3L, "propietario@test.com", "PROPIETARIO");

        mockMvc.perform(get("/api/security/dashboard/status")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_accessingSuperAdminEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(2L, "admin@test.com", "ADMINISTRADOR_CONDOMINIO");

        mockMvc.perform(get("/api/super-admin/administrators")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_accessingPropietarioEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(2L, "admin@test.com", "ADMINISTRADOR_CONDOMINIO");

        mockMvc.perform(get("/api/homeowner/dashboard/summary")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_accessingSecurityEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(2L, "admin@test.com", "ADMINISTRADOR_CONDOMINIO");

        mockMvc.perform(get("/api/security/dashboard/status")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void superAdmin_accessingAdminEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");

        mockMvc.perform(get("/api/admin/dashboard/metrics")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void superAdmin_accessingPropietarioEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");

        mockMvc.perform(get("/api/homeowner/dashboard/summary")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void superAdmin_accessingSecurityEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");

        mockMvc.perform(get("/api/security/dashboard/status")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void agente_accessingSuperAdminEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(4L, "agente@test.com", "AGENTE_SEGURIDAD");

        mockMvc.perform(get("/api/super-admin/administrators")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void agente_accessingAdminEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(4L, "agente@test.com", "AGENTE_SEGURIDAD");

        mockMvc.perform(get("/api/admin/dashboard/metrics")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void agente_accessingPropietarioEndpoint_returns403() throws Exception {
        var token = JwtTestUtil.accessToken(4L, "agente@test.com", "AGENTE_SEGURIDAD");

        mockMvc.perform(get("/api/homeowner/dashboard/summary")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    // -- 200: Correct role --

    @Test
    void superAdmin_accessingCatalogoEndpoint_returns200() throws Exception {
        var token = JwtTestUtil.accessToken(1L, "super@test.com", "SUPER_ADMINISTRADOR");

        mockMvc.perform(get("/api/catalogs/countries")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

}
