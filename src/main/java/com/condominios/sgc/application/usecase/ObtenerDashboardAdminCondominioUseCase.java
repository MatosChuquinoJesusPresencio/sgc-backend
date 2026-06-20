package com.condominios.sgc.application.usecase;

import com.condominios.sgc.infrastructure.web.dto.DashboardAdminCondominioResponse;

public interface ObtenerDashboardAdminCondominioUseCase {
    DashboardAdminCondominioResponse ejecutar(Long condominioId);
}
