package com.condominios.sgc.application.dto.result;

import java.util.List;

public record DashboardSAResult(
    int totalCondominios,
    int totalAdministradores,
    int totalUsuarios,
    int usuariosActivos,
    int usuariosBloqueados,
    int totalPropietarios,
    int totalApartamentos,
    List<CondominioResumen> ultimosCondominios,
    List<UsuarioResumen> ultimosUsuarios
) {}
