package com.condominios.sgc.web.dto;

import java.util.List;

public record DashboardSuperAdminResponse(
    long totalCondominios,
    long totalUsuarios,
    long totalTorres,
    long totalPrestamos,
    long totalAccesos,
    long usuariosActivos,
    List<CondominioResumen> condominiosRecientes,
    List<ActividadCondominio> actividadPorCondominio
) {}
