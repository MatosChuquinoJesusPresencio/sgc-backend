package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.result.AdministradorResult;
import com.condominios.sgc.application.dto.result.CondominioSimpleResult;
import com.condominios.sgc.application.dto.result.DashboardMetricsResult;

public interface GestionarDashboardUseCase {
    DashboardMetricsResult obtenerMetricas();
    List<AdministradorResult> obtenerAdministradoresRecientes();
    List<CondominioSimpleResult> obtenerCondominiosRecientes();
}
