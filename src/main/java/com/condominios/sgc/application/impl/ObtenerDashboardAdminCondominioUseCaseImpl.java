package com.condominios.sgc.application.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.condominios.sgc.application.usecase.ObtenerDashboardAdminCondominioUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.EstacionamientoPort;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.web.dto.DashboardAdminCondominioResponse;
import com.condominios.sgc.infrastructure.web.dto.LogAccesoVehicularResponse;
import com.condominios.sgc.infrastructure.web.dto.LogPrestamoCarritoResponse;
import com.condominios.sgc.infrastructure.web.dto.DashboardAdminCondominioResponse.CondominioInfo;
import com.condominios.sgc.infrastructure.web.dto.DashboardAdminCondominioResponse.ConfigData;

public class ObtenerDashboardAdminCondominioUseCaseImpl implements ObtenerDashboardAdminCondominioUseCase {

    private static final PaginacionRequest ALL = new PaginacionRequest(0, Integer.MAX_VALUE, null, null, null);
    private static final PaginacionRequest COUNT_ONE = new PaginacionRequest(0, 1, null, null, null);
    private static final PaginacionRequest RECENT_ACCESS = new PaginacionRequest(0, 5, "fechaEntrada", "DESC", null);
    private static final PaginacionRequest RECENT_LOANS = new PaginacionRequest(0, 5, "fechaPrestamo", "DESC", null);

    private final CondominioPort condominioPort;
    private final UsuarioPort usuarioPort;
    private final TorrePort torrePort;
    private final PisoPort pisoPort;
    private final ApartamentoPort apartamentoPort;
    private final EstacionamientoPort estacionamientoPort;
    private final ConfiguracionPort configuracionPort;
    private final LogPrestamoCarritoPort logPrestamoPort;
    private final LogAccesoVehicularPort logAccesoPort;

    public ObtenerDashboardAdminCondominioUseCaseImpl(
            CondominioPort condominioPort,
            UsuarioPort usuarioPort,
            TorrePort torrePort,
            PisoPort pisoPort,
            ApartamentoPort apartamentoPort,
            EstacionamientoPort estacionamientoPort,
            ConfiguracionPort configuracionPort,
            LogPrestamoCarritoPort logPrestamoPort,
            LogAccesoVehicularPort logAccesoPort) {
        this.condominioPort = condominioPort;
        this.usuarioPort = usuarioPort;
        this.torrePort = torrePort;
        this.pisoPort = pisoPort;
        this.apartamentoPort = apartamentoPort;
        this.estacionamientoPort = estacionamientoPort;
        this.configuracionPort = configuracionPort;
        this.logPrestamoPort = logPrestamoPort;
        this.logAccesoPort = logAccesoPort;
    }

    @Override
    public DashboardAdminCondominioResponse ejecutar(Long condominioId) {
        var condominio = condominioPort.findById(condominioId)
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado: " + condominioId));

        var condominioInfo = new CondominioInfo(
                condominio.getId(),
                condominio.getNombre(),
                condominio.getPais(),
                condominio.getCiudad(),
                condominio.getDireccion());

        var torresPage = torrePort.findByCondominioId(condominioId, ALL);
        var torres = torresPage.contenido();
        long totalTorres = torres.size();

        List<Long> pisoIds = new ArrayList<>();
        long totalPisos = 0;
        for (var torre : torres) {
            var pisos = pisoPort.findByTorreId(torre.getId(), ALL).contenido();
            totalPisos += pisos.size();
            pisos.stream().map(p -> p.getId()).forEach(pisoIds::add);
        }

        long totalApartamentos = 0;
        for (Long pisoId : pisoIds) {
            totalApartamentos += apartamentoPort.findByPisoId(pisoId, COUNT_ONE).totalElementos();
        }

        var userFilter = new PaginacionRequest(0, Integer.MAX_VALUE, null, null,
                Map.of("condominioId", String.valueOf(condominioId)));
        var usuarios = usuarioPort.findAll(userFilter).contenido();
        long totalPropietarios = usuarios.stream().filter(u -> u.getRol() == Rol.PROPIETARIO).count();
        long totalSeguridad = usuarios.stream().filter(u -> u.getRol() == Rol.AGENTE_SEGURIDAD).count();

        var estacionamientos = estacionamientoPort.findByCondominioId(condominioId, ALL).contenido();
        long totalEstacionamientos = estacionamientos.size();
        long estacionamientosOcupados = estacionamientos.stream()
                .filter(e -> e.getCantidadActual() != null && e.getCantidadActual() > 0)
                .count();

        var accesos = logAccesoPort.findByCondominioId(condominioId, ALL).contenido();
        long vehiculosEnRecinto = accesos.stream()
                .filter(a -> a.getFechaSalida() == null)
                .count();

        var prestamos = logPrestamoPort.findByCondominioId(condominioId, ALL).contenido();
        long carritosEnUso = prestamos.stream()
                .filter(p -> p.getFechaDevolucion() == null)
                .count();

        var recentAccesses = logAccesoPort.findByCondominioId(condominioId, RECENT_ACCESS).contenido()
                .stream()
                .map(LogAccesoVehicularResponse::fromModel)
                .toList();

        var recentLoans = logPrestamoPort.findByCondominioId(condominioId, RECENT_LOANS).contenido()
                .stream()
                .map(LogPrestamoCarritoResponse::fromModel)
                .toList();

        ConfigData configData = null;
        var configOpt = configuracionPort.findByCondominioId(condominioId);
        if (configOpt.isPresent()) {
            var c = configOpt.get();
            configData = new ConfigData(
                    c.getMaxAutos() != null ? c.getMaxAutos() : 0,
                    c.getMaxMotos() != null ? c.getMaxMotos() : 0,
                    c.getPenalizacionPorMin(),
                    c.getMaxTiempoPrestamoMin() != null ? c.getMaxTiempoPrestamoMin() : 0);
        }

        return new DashboardAdminCondominioResponse(
                condominioInfo,
                totalTorres,
                totalPisos,
                totalApartamentos,
                totalPropietarios,
                totalSeguridad,
                totalEstacionamientos,
                estacionamientosOcupados,
                vehiculosEnRecinto,
                carritosEnUso,
                recentAccesses,
                recentLoans,
                configData);
    }
}
