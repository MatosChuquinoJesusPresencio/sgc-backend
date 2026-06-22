package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.AdminDashboardMetricsResult;
import com.condominios.sgc.application.port.in.GestionarAdminDashboardUseCase;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class GestionarAdminDashboardService implements GestionarAdminDashboardUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioRepositoryPort condominioRepository;
    private final VehiculoRepositoryPort vehiculoRepository;
    private final CarritoRepositoryPort carritoRepository;

    public GestionarAdminDashboardService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            VehiculoRepositoryPort vehiculoRepository,
            CarritoRepositoryPort carritoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.carritoRepository = carritoRepository;
    }

    @Override
    public AdminDashboardMetricsResult obtenerMetricas() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        var condominio = condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);

        long totalTorres = condominio.getTorres().size();
        long totalPisos = condominio.getTorres().stream()
            .mapToLong(t -> t.getPisos().size())
            .sum();
        long totalApartamentos = condominio.getTorres().stream()
            .flatMap(t -> t.getPisos().stream())
            .mapToLong(p -> p.getApartamentos().size())
            .sum();
        long totalPropietarios = usuarioRepository.contarPorCondominioYRol(condominioId, "PROPIETARIO");
        long totalAgentes = usuarioRepository.contarPorCondominioYRol(condominioId, "AGENTE_SEGURIDAD");
        long totalVehiculos = vehiculoRepository.contarPorCondominio(condominioId);
        long totalCarritos = carritoRepository.contarPorCondominio(condominioId);

        return new AdminDashboardMetricsResult(
            totalTorres, totalPisos, totalApartamentos,
            totalPropietarios, totalAgentes,
            totalVehiculos, totalCarritos
        );
    }
}
