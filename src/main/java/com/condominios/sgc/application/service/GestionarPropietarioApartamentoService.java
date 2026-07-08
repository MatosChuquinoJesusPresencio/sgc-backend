package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.result.PropietarioApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.PropietarioInquilinoResult;
import com.condominios.sgc.application.dto.result.PropietarioVehiculoResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarPropietarioApartamentoUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class GestionarPropietarioApartamentoService implements GestionarPropietarioApartamentoUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioIdResolver condominioIdResolver;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final CondominioRepositoryPort condominioRepository;
    private final InquilinoRepositoryPort inquilinoRepository;
    private final VehiculoRepositoryPort vehiculoRepository;

    public GestionarPropietarioApartamentoService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioIdResolver condominioIdResolver,
            ApartamentoRepositoryPort apartamentoRepository,
            CondominioRepositoryPort condominioRepository,
            InquilinoRepositoryPort inquilinoRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.condominioIdResolver = condominioIdResolver;
        this.apartamentoRepository = apartamentoRepository;
        this.condominioRepository = condominioRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public PropietarioApartamentoDetailResult obtenerDetalle(Long condominioIdOverride) {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var apt = apartamentoRepository.buscarPorPropietario(usuario.getId());
        if (apt.isEmpty()) throw ApartamentoException.noEncontrado();
        var apto = apt.get();

        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var condominio = condominioRepository.buscarPorId(condominioId).orElse(null);
        String torreNombre = null;
        Integer pisoNumero = null;
        if (condominio != null) {
            outer:
            for (var torre : condominio.getTorres()) {
                for (var piso : torre.getPisos()) {
                    if (piso.getApartamentos().stream().anyMatch(a -> a.getId().equals(apto.getId()))) {
                        torreNombre = torre.getNombre();
                        pisoNumero = piso.getNumero();
                        break outer;
                    }
                }
            }
        }

        var inquilinos = inquilinoRepository.buscarPorApartamento(apto.getId())
            .stream()
            .map(i -> new PropietarioInquilinoResult(
                i.getId(),
                i.getNombreCompleto().nombres(),
                i.getNombreCompleto().apellidos(),
                i.getNumeroDocumento().tipo().name(),
                i.getNumeroDocumento().numero()))
            .toList();

        var vehiculos = vehiculoRepository.buscarPorPropietario(usuario.getId())
            .stream()
            .map(v -> new PropietarioVehiculoResult(
                v.getId(), v.getMarca(), v.getColor(), v.getModelo(),
                v.getPlaca().valor(), v.getTipo().name(), v.getIdEstacionamiento()))
            .toList();

        return new PropietarioApartamentoDetailResult(
            apto.getId(), apto.getNumero(), apto.getMetraje(),
            apto.getDerechoEstacionamiento(), torreNombre, pisoNumero,
            inquilinos.size(), vehiculos.size(), inquilinos, vehiculos);
    }
}
