package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.RegistrarEntradaVehiculoCommand;
import com.condominios.sgc.application.dto.command.RegistrarSalidaVehiculoCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarSeguridadAccesoUseCase;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.shared.exception.EstacionamientoException;
import com.condominios.sgc.domain.shared.exception.LogAccesoVehicularException;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarSeguridadAccesoService implements GestionarSeguridadAccesoUseCase {

    private final CondominioIdResolver condominioIdResolver;
    private final VehiculoRepositoryPort vehiculoRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;
    private final LogAccesoVehicularRepositoryPort logAccesoRepository;

    public GestionarSeguridadAccesoService(
            CondominioIdResolver condominioIdResolver,
            VehiculoRepositoryPort vehiculoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository) {
        this.condominioIdResolver = condominioIdResolver;
        this.vehiculoRepository = vehiculoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
        this.logAccesoRepository = logAccesoRepository;
    }

    @Override
    @Transactional
    public AdminLogEntryResult registrarEntrada(Long condominioIdOverride, RegistrarEntradaVehiculoCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);

        var vehiculo = vehiculoRepository.buscarPorPlaca(cmd.placa())
            .orElseThrow(com.condominios.sgc.domain.shared.exception.VehiculoException::noEncontrado);

        EstacionamientoModel slot;
        if (cmd.idEstacionamiento() != null) {
            slot = estacionamientoRepository.buscarPorId(cmd.idEstacionamiento())
                .orElseThrow(() -> EstacionamientoException.noEncontrado());
            if (!slot.hayEspacio()) {
                throw EstacionamientoException.sinEspacio();
            }
        } else {
            var estacionamientos = estacionamientoRepository.buscarPorCondominio(condominioId, new PaginaQuery(0, Integer.MAX_VALUE));
            slot = estacionamientos.items().stream()
                .filter(e -> e.hayEspacio())
                .findFirst()
                .orElseThrow(EstacionamientoException::sinEspacio);
        }

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
    @Transactional
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
            m.getId(),
            "VEHICULAR",
            m.getPlaca().valor(),
            m.getOcupante().name(),
            m.getDatosInquilino(),
            m.getMetodo().name(),
            m.getFechaEntrada() != null ? m.getFechaEntrada() : null,
            m.getFechaSalida() != null ? m.getFechaSalida() : null,
            null, null, null, null, null, null,
            m.getIdCondominio(), m.getIdVehiculo(), m.getIdEstacionamiento());
    }
}
