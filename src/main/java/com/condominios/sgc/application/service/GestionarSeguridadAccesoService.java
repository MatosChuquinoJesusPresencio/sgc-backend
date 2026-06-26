package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.RegistrarEntradaVehiculoCommand;
import com.condominios.sgc.application.dto.command.RegistrarSalidaVehiculoCommand;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.port.in.GestionarSeguridadAccesoUseCase;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.shared.exception.EstacionamientoException;
import com.condominios.sgc.domain.shared.exception.LogAccesoVehicularException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.shared.exception.VehiculoException;

public class GestionarSeguridadAccesoService implements GestionarSeguridadAccesoUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final VehiculoRepositoryPort vehiculoRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;
    private final LogAccesoVehicularRepositoryPort logAccesoRepository;

    public GestionarSeguridadAccesoService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            VehiculoRepositoryPort vehiculoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
        this.logAccesoRepository = logAccesoRepository;
    }

    @Override
    public AdminLogEntryResult registrarEntrada(RegistrarEntradaVehiculoCommand cmd) {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();

        var vehiculo = vehiculoRepository.buscarPorPlaca(cmd.placa())
            .orElseThrow(VehiculoException::noEncontrado);

        var estacionamientos = estacionamientoRepository.buscarPorCondominio(condominioId);
        var slot = estacionamientos.stream()
            .filter(e -> e.hayEspacio())
            .findFirst()
            .orElseThrow(EstacionamientoException::sinEspacio);

        slot.incrementarOcupacion();
        estacionamientoRepository.guardar(slot);
        vehiculo.asignarEstacionamiento(slot.getId());
        vehiculoRepository.guardar(vehiculo);

        var log = new LogAccesoVehicularModel(
            cmd.placa(), cmd.ocupante(), cmd.datosInquilino(),
            cmd.metodo(), vehiculo.getId(), slot.getId(), condominioId);
        var guardado = logAccesoRepository.guardar(log);
        return toResult(guardado);
    }

    @Override
    public AdminLogEntryResult registrarSalida(RegistrarSalidaVehiculoCommand cmd) {
        var log = logAccesoRepository.buscarPorId(cmd.idLogAcceso())
            .orElseThrow(LogAccesoVehicularException::noEncontrado);
        log.registrarSalida();
        logAccesoRepository.guardar(log);

        var estacionamiento = estacionamientoRepository.buscarPorId(log.getIdEstacionamiento()).orElse(null);
        if (estacionamiento != null) {
            estacionamiento.decrementarOcupacion();
            estacionamientoRepository.guardar(estacionamiento);
        }

        var vehiculo = vehiculoRepository.buscarPorId(log.getIdVehiculo()).orElse(null);
        if (vehiculo != null) {
            vehiculo.desasignarEstacionamiento();
            vehiculoRepository.guardar(vehiculo);
        }

        return toResult(log);
    }

    private AdminLogEntryResult toResult(LogAccesoVehicularModel m) {
        return new AdminLogEntryResult(
            m.getId(), "VEHICULAR",
            m.getPlaca().valor(), m.getOcupante().name(), m.getDatosInquilino(),
            m.getMetodo().name(),
            m.getFechaEntrada(),
            m.getFechaSalida(),
            null, null, null, null, null, null,
            m.getIdCondominio());
    }
}
