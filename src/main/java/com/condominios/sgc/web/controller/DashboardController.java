package com.condominios.sgc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.usecase.ObtenerDashboardSuperAdminUseCase;
import com.condominios.sgc.web.dto.DashboardSuperAdminResponse;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ObtenerDashboardSuperAdminUseCase dashboardUseCase;

    public DashboardController(ObtenerDashboardSuperAdminUseCase dashboardUseCase) {
        this.dashboardUseCase = dashboardUseCase;
    }

    @GetMapping("/super-admin")
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<DashboardSuperAdminResponse> obtenerDashboard() {
        return ResponseEntity.ok(dashboardUseCase.ejecutar());
    }
}
