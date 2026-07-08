package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.AdminDashboardMetricsResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarAdminDashboardUseCase;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.domain.shared.exception.CondominioException;

public class GestionarAdminDashboardService implements GestionarAdminDashboardUseCase {

    private final CondominioRepositoryPort condominioRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final VehiculoRepositoryPort vehiculoRepository;
    private final CarritoRepositoryPort carritoRepository;
    private final CondominioIdResolver condominioIdResolver;

    public GestionarAdminDashboardService(
            CondominioRepositoryPort condominioRepository,
            UsuarioRepositoryPort usuarioRepository,
            VehiculoRepositoryPort vehiculoRepository,
            CarritoRepositoryPort carritoRepository,
            CondominioIdResolver condominioIdResolver) {
        this.condominioRepository = condominioRepository;
        this.usuarioRepository = usuarioRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.carritoRepository = carritoRepository;
        this.condominioIdResolver = condominioIdResolver;
    }

    @Override
    public AdminDashboardMetricsResult obtenerMetricas(Long condominioIdOverride) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
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
