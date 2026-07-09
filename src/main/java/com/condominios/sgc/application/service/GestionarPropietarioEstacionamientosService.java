package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.result.PropietarioEstacionamientoResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarPropietarioEstacionamientosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarPropietarioEstacionamientosService implements GestionarPropietarioEstacionamientosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioIdResolver condominioIdResolver;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;

    public GestionarPropietarioEstacionamientosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioIdResolver condominioIdResolver,
            ApartamentoRepositoryPort apartamentoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.condominioIdResolver = condominioIdResolver;
        this.apartamentoRepository = apartamentoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
    }

    @Override
    public List<PropietarioEstacionamientoResult> listar(Long condominioIdOverride) {
        condominioIdResolver.resolver(condominioIdOverride);
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var apt = apartamentoRepository.buscarPorPropietario(usuario.getId());
        if (apt.isEmpty()) throw ApartamentoException.noEncontrado();
        return estacionamientoRepository.buscarPorApartamento(apt.get().getId())
            .stream()
            .map(e -> new PropietarioEstacionamientoResult(
                e.getId(),
                e.getNumero(),
                e.getTipoVehiculo() != null ? e.getTipoVehiculo().name() : null,
                e.getCapacidadMaxima(),
                e.getCantidadActual(),
                e.getDisponible()))
            .toList();
    }
}
