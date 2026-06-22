package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.result.AdministradorResult;
import com.condominios.sgc.application.dto.result.CondominioSimpleResult;
import com.condominios.sgc.application.dto.result.DashboardMetricsResult;
import com.condominios.sgc.application.port.in.GestionarDashboardUseCase;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;

public class GestionarDashboardService implements GestionarDashboardUseCase {

    private static final int LIMITE_RECIENTES = 5;

    private final CondominioRepositoryPort condominioRepository;
    private final UsuarioRepositoryPort usuarioRepository;

    public GestionarDashboardService(
            CondominioRepositoryPort condominioRepository,
            UsuarioRepositoryPort usuarioRepository) {
        this.condominioRepository = condominioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public DashboardMetricsResult obtenerMetricas() {
        long totalCondominios = condominioRepository.contarTodos(null, null);
        long condominiosActivos = condominioRepository.contarActivos();
        long totalAdministradores = usuarioRepository.contarPorRol("ADMINISTRADOR_CONDOMINIO");
        long totalPropietarios = usuarioRepository.contarPorRol("PROPIETARIO");
        long totalAgentes = usuarioRepository.contarPorRol("AGENTE_SEGURIDAD");
        long totalUsuarios = totalAdministradores + totalPropietarios + totalAgentes;
        return new DashboardMetricsResult(
            totalCondominios, condominiosActivos,
            totalAdministradores, totalPropietarios, totalAgentes, totalUsuarios
        );
    }

    @Override
    public List<AdministradorResult> obtenerAdministradoresRecientes() {
        return usuarioRepository.buscarRecientesPorRol("ADMINISTRADOR_CONDOMINIO", LIMITE_RECIENTES)
            .stream()
            .map(u -> {
                String nombreCondominio = null;
                if (u.getIdCondominio() != null) {
                    nombreCondominio = condominioRepository.buscarNombrePorId(u.getIdCondominio()).orElse(null);
                }
                return new AdministradorResult(
                    u.getId(), u.getNombres(), u.getApellidos(),
                    u.getCorreo().direccion(),
                    u.getTelefono() != null ? u.getTelefono().numero() : null,
                    u.getActivo(), u.getIdCondominio(), nombreCondominio,
                    u.getFechaCreacion()
                );
            })
            .toList();
    }

    @Override
    public List<CondominioSimpleResult> obtenerCondominiosRecientes() {
        return condominioRepository.buscarRecientes(LIMITE_RECIENTES)
            .stream()
            .map(c -> new CondominioSimpleResult(c.getId(), c.getNombre()))
            .toList();
    }
}
