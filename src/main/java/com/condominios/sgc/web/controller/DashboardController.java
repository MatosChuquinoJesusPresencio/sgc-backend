package com.condominios.sgc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.service.UsuarioService;
import com.condominios.sgc.application.usecase.ObtenerDashboardAdminCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerDashboardSuperAdminUseCase;
import com.condominios.sgc.infrastructure.util.SecurityUtils;
import com.condominios.sgc.web.dto.DashboardAdminCondominioResponse;
import com.condominios.sgc.web.dto.DashboardSuperAdminResponse;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ObtenerDashboardSuperAdminUseCase dashboardUseCase;
    private final ObtenerDashboardAdminCondominioUseCase adminCondominioDashboardUseCase;
    private final UsuarioService usuarioService;

    public DashboardController(
            ObtenerDashboardSuperAdminUseCase dashboardUseCase,
            ObtenerDashboardAdminCondominioUseCase adminCondominioDashboardUseCase,
            UsuarioService usuarioService) {
        this.dashboardUseCase = dashboardUseCase;
        this.adminCondominioDashboardUseCase = adminCondominioDashboardUseCase;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/super-admin")
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<DashboardSuperAdminResponse> obtenerDashboard() {
        return ResponseEntity.ok(dashboardUseCase.ejecutar());
    }

    @GetMapping("/admin-condominio")
    @PreAuthorize("hasRole('ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<DashboardAdminCondominioResponse> obtenerDashboardAdminCondominio() {
        Long userId = SecurityUtils.obtenerIdUsuario();
        var usuario = usuarioService.obtener(userId);
        var condominioId = usuario.getCondominioId();
        if (condominioId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(adminCondominioDashboardUseCase.ejecutar(condominioId));
    }
}
