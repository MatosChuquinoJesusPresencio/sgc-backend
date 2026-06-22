package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.result.AdminDashboardMetricsResult;

public interface GestionarAdminDashboardUseCase {
    AdminDashboardMetricsResult obtenerMetricas();
}
