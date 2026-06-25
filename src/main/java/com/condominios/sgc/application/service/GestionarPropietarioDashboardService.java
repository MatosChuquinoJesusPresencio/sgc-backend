package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.PropietarioDashboardResult;
import com.condominios.sgc.application.port.in.GestionarPropietarioDashboardUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class GestionarPropietarioDashboardService implements GestionarPropietarioDashboardUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final CondominioRepositoryPort condominioRepository;
    private final InquilinoRepositoryPort inquilinoRepository;
    private final VehiculoRepositoryPort vehiculoRepository;

    public GestionarPropietarioDashboardService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            CondominioRepositoryPort condominioRepository,
            InquilinoRepositoryPort inquilinoRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.condominioRepository = condominioRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public PropietarioDashboardResult obtenerResumen() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var aptos = apartamentoRepository.buscarPorPropietario(usuario.getId());
        if (aptos.isEmpty()) {
            return new PropietarioDashboardResult(null, null, null, null, 0, 0, 0);
        }
        var apto = aptos.get(0);
        var condominio = condominioRepository.buscarPorId(usuario.getIdCondominio()).orElse(null);

        String torreNombre = null;
        Integer pisoNumero = null;
        if (condominio != null) {
            for (var torre : condominio.getTorres()) {
                for (var piso : torre.getPisos()) {
                    if (piso.getApartamentos().stream().anyMatch(a -> a.getId().equals(apto.getId()))) {
                        torreNombre = torre.getNombre();
                        pisoNumero = piso.getNumero();
                        break;
                    }
                }
                if (torreNombre != null) break;
            }
        }

        int totalInquilinos = inquilinoRepository.buscarPorApartamento(apto.getId()).size();
        int totalVehiculos = vehiculoRepository.buscarPorPropietario(usuario.getId()).size();

        return new PropietarioDashboardResult(
            apto.getId(), apto.getNumero(), torreNombre, pisoNumero,
            totalInquilinos, totalVehiculos, 0);
    }
}
