package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.result.PropietarioDashboardResult;

public interface GestionarPropietarioDashboardUseCase {
    PropietarioDashboardResult obtenerResumen(Long condominioIdOverride);
}
