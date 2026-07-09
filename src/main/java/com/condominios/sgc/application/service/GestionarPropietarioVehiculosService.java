package com.condominios.sgc.application.service;

import java.util.List;
import java.util.stream.Stream;

import com.condominios.sgc.application.dto.command.CrearPropietarioVehiculoCommand;
import com.condominios.sgc.application.dto.result.PropietarioVehiculoResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarPropietarioVehiculosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.EstacionamientoException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.shared.exception.VehiculoException;
import com.condominios.sgc.domain.type.TipoVehiculo;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarPropietarioVehiculosService implements GestionarPropietarioVehiculosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioIdResolver condominioIdResolver;
    private final VehiculoRepositoryPort vehiculoRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;
    private final InquilinoRepositoryPort inquilinoRepository;

    public GestionarPropietarioVehiculosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioIdResolver condominioIdResolver,
            VehiculoRepositoryPort vehiculoRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            InquilinoRepositoryPort inquilinoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.condominioIdResolver = condominioIdResolver;
        this.vehiculoRepository = vehiculoRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
        this.inquilinoRepository = inquilinoRepository;
    }

    private Long obtenerIdPropietario() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        return usuario.getId();
    }

    @Override
    public List<PropietarioVehiculoResult> listar() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var propios = vehiculoRepository.buscarPorPropietario(usuario.getId());
        var inquilinosVehiculos = apartamentoRepository.buscarPorPropietario(usuario.getId())
            .map(apt -> inquilinoRepository.buscarPorApartamento(apt.getId())
                .stream()
                .flatMap(i -> vehiculoRepository.buscarPorInquilino(i.getId()).stream())
                .toList())
            .orElse(List.of());
        return Stream.concat(propios.stream(), inquilinosVehiculos.stream())
            .map(this::toResult)
            .toList();
    }

    @Override
    @Transactional
    public PropietarioVehiculoResult crear(Long condominioIdOverride, CrearPropietarioVehiculoCommand cmd) {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var modelo = new VehiculoModel(
            cmd.marca(), cmd.color(), cmd.modelo(), cmd.placa(),
            TipoVehiculo.valueOf(cmd.tipo()),
            condominioId, usuario.getId(), null);
        return toResult(vehiculoRepository.guardar(modelo));
    }

    @Override
    @Transactional
    public PropietarioVehiculoResult asignarEstacionamiento(Long condominioIdOverride, Long vehiculoId, Long idEstacionamiento) {
        condominioIdResolver.resolver(condominioIdOverride);
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var vehiculo = vehiculoRepository.buscarPorId(vehiculoId)
            .orElseThrow(VehiculoException::noEncontrado);
        var apt = apartamentoRepository.buscarPorPropietario(usuario.getId())
            .orElseThrow(ApartamentoException::noEncontrado);
        boolean esPropio = usuario.getId().equals(vehiculo.getIdPropietario());
        boolean esDeInquilino = vehiculo.getIdInquilino() != null
            && inquilinoRepository.buscarPorId(vehiculo.getIdInquilino())
                .map(i -> i.getIdApartamento().equals(apt.getId()))
                .orElse(false);
        if (!esPropio && !esDeInquilino) {
            throw VehiculoException.noEncontrado();
        }

        if (idEstacionamiento != null) {
            var estacionamiento = estacionamientoRepository.buscarPorId(idEstacionamiento)
                .orElseThrow(EstacionamientoException::noEncontrado);
            if (!apt.getId().equals(estacionamiento.getIdApartamento())) {
                throw EstacionamientoException.noEncontrado();
            }
            vehiculo.asignarEstacionamiento(idEstacionamiento);
        } else {
            vehiculo.desasignarEstacionamiento();
        }

        return toResult(vehiculoRepository.guardar(vehiculo));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        var vehiculo = vehiculoRepository.buscarPorId(id)
            .orElseThrow(VehiculoException::noEncontrado);
        var idPropietario = obtenerIdPropietario();
        if (!idPropietario.equals(vehiculo.getIdPropietario())) {
            throw VehiculoException.noEncontrado();
        }
        vehiculoRepository.eliminarPorId(id);
    }

    private PropietarioVehiculoResult toResult(VehiculoModel m) {
        return new PropietarioVehiculoResult(
            m.getId(), m.getMarca(), m.getColor(), m.getModelo(),
            m.getPlaca().valor(), m.getTipo().name(), m.getIdEstacionamiento());
    }
}
