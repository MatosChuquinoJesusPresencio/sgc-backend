package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.RegistrarEntradaVehiculoCommand;
import com.condominios.sgc.application.dto.command.RegistrarSalidaVehiculoCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarSeguridadAccesoUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
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
    private final UsuarioRepositoryPort usuarioRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final InquilinoRepositoryPort inquilinoRepository;

    public GestionarSeguridadAccesoService(
            CondominioIdResolver condominioIdResolver,
            VehiculoRepositoryPort vehiculoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository,
            UsuarioRepositoryPort usuarioRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            InquilinoRepositoryPort inquilinoRepository) {
        this.condominioIdResolver = condominioIdResolver;
        this.vehiculoRepository = vehiculoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
        this.logAccesoRepository = logAccesoRepository;
        this.usuarioRepository = usuarioRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.inquilinoRepository = inquilinoRepository;
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

    @Override
    public PaginaResult<AdminLogEntryResult> listarLogs(Long condominioIdOverride, PaginaQuery pagina) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var resultados = logAccesoRepository.buscarPorCondominio(condominioId, null, null, null, pagina);
        var items = resultados.items().stream()
            .map(this::toResult)
            .toList();
        return new PaginaResult<>(items, resultados.total(), resultados.pagina(), resultados.tamano());
    }

    private AdminLogEntryResult toResult(LogAccesoVehicularModel m) {
        String nombrePropietario = null, torreNombre = null;
        Integer numeroDepartamento = null;
        String marca = null, modelo = null, color = null, tipoVehiculo = null;

        if (m.getIdVehiculo() != null) {
            var vehiculoOpt = vehiculoRepository.buscarPorId(m.getIdVehiculo());
            if (vehiculoOpt.isPresent()) {
                var v = vehiculoOpt.get();
                marca = v.getMarca();
                modelo = v.getModelo();
                color = v.getColor();
                tipoVehiculo = v.getTipo() != null ? v.getTipo().name() : null;

                if (v.getIdPropietario() != null) {
                    var usuarioOpt = usuarioRepository.buscarPorId(v.getIdPropietario());
                    if (usuarioOpt.isPresent()) {
                        nombrePropietario = usuarioOpt.get().getNombreCompleto().nombres()
                            + " " + usuarioOpt.get().getNombreCompleto().apellidos();
                    }
                    var torreInfo = apartamentoRepository.buscarTorreYAptoPorPropietario(v.getIdPropietario());
                    if (torreInfo.isPresent()) {
                        torreNombre = torreInfo.get().torreNombre();
                        numeroDepartamento = torreInfo.get().numeroDepartamento();
                    }
                }
                if (nombrePropietario == null && v.getIdInquilino() != null) {
                    var inquilinoOpt = inquilinoRepository.buscarPorId(v.getIdInquilino());
                    if (inquilinoOpt.isPresent()) {
                        var inquilino = inquilinoOpt.get();
                        nombrePropietario = inquilino.getNombreCompleto().nombres()
                            + " " + inquilino.getNombreCompleto().apellidos();
                        var torreInfo = apartamentoRepository.buscarTorreYAptoPorId(inquilino.getIdApartamento());
                        if (torreInfo.isPresent()) {
                            torreNombre = torreInfo.get().torreNombre();
                            numeroDepartamento = torreInfo.get().numeroDepartamento();
                        }
                    }
                }
            }
        }

        return new AdminLogEntryResult(
            m.getId(), "VEHICULAR", m.getPlaca().valor(),
            m.getOcupante().name(), m.getDatosInquilino(),
            m.getMetodo().name(), m.getFechaEntrada(), m.getFechaSalida(),
            null, null, null, null, null, null,
            m.getIdCondominio(), m.getIdVehiculo(), m.getIdEstacionamiento(),
            nombrePropietario, torreNombre, numeroDepartamento,
            marca, modelo, color, tipoVehiculo);
    }
}
