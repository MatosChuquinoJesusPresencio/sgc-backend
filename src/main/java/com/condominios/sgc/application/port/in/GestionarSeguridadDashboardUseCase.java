package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.result.SecurityDashboardResult;

public interface GestionarSeguridadDashboardUseCase {
    SecurityDashboardResult obtenerStatus();
}
