package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.command.CrearPropietarioVehiculoCommand;
import com.condominios.sgc.application.dto.result.PropietarioVehiculoResult;
import com.condominios.sgc.application.port.in.GestionarPropietarioVehiculosUseCase;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.shared.exception.VehiculoException;
import com.condominios.sgc.domain.type.TipoVehiculo;
import org.springframework.transaction.annotation.Transactional;

public class GestionarPropietarioVehiculosService implements GestionarPropietarioVehiculosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final VehiculoRepositoryPort vehiculoRepository;

    public GestionarPropietarioVehiculosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    private Long obtenerIdPropietario() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        return usuario.getId();
    }

    @Override
    public List<PropietarioVehiculoResult> listar() {
        return vehiculoRepository.buscarPorPropietario(obtenerIdPropietario())
            .stream()
            .map(this::toResult)
            .toList();
    }

    @Transactional
    @Override
    public PropietarioVehiculoResult crear(CrearPropietarioVehiculoCommand cmd) {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var modelo = new VehiculoModel(
            cmd.marca(), cmd.color(), cmd.modelo(), cmd.placa(),
            TipoVehiculo.valueOf(cmd.tipo()),
            usuario.getIdCondominio(), usuario.getId(), null);
        return toResult(vehiculoRepository.guardar(modelo));
    }

    @Transactional
    @Override
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
