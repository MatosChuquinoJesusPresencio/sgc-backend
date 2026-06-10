package com.condominios.sgc.application.impl;

import java.util.Comparator;

import com.condominios.sgc.application.usecase.ObtenerDashboardSuperAdminUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.web.dto.ActividadCondominio;
import com.condominios.sgc.web.dto.CondominioResumen;
import com.condominios.sgc.web.dto.DashboardSuperAdminResponse;

public class ObtenerDashboardSuperAdminUseCaseImpl implements ObtenerDashboardSuperAdminUseCase {

    private static final PaginacionRequest ALL = new PaginacionRequest(0, Integer.MAX_VALUE, null, null, null);
    private static final PaginacionRequest COUNT_ONE = new PaginacionRequest(0, 1, null, null, null);

    private final CondominioPort condominioPort;
    private final UsuarioPort usuarioPort;
    private final TorrePort torrePort;
    private final LogPrestamoCarritoPort logPrestamoPort;
    private final LogAccesoVehicularPort logAccesoPort;

    public ObtenerDashboardSuperAdminUseCaseImpl(
            CondominioPort condominioPort,
            UsuarioPort usuarioPort,
            TorrePort torrePort,
            LogPrestamoCarritoPort logPrestamoPort,
            LogAccesoVehicularPort logAccesoPort) {
        this.condominioPort = condominioPort;
        this.usuarioPort = usuarioPort;
        this.torrePort = torrePort;
        this.logPrestamoPort = logPrestamoPort;
        this.logAccesoPort = logAccesoPort;
    }

    @Override
    public DashboardSuperAdminResponse ejecutar() {
        var condominiosPage = condominioPort.findAll(ALL);
        var condominios = condominiosPage.contenido();

        var usuariosPage = usuarioPort.findAll(ALL);
        var usuarios = usuariosPage.contenido();

        long totalUsuarios = usuarios.size();
        long usuariosActivos = usuarios.stream().filter(UsuarioModel::isActivo).count();

        long totalTorres = 0;
        long totalPrestamos = 0;
        long totalAccesos = 0;

        for (CondominioModel c : condominios) {
            totalTorres += torrePort.findByCondominioId(c.getId(), COUNT_ONE).totalElementos();
            totalPrestamos += logPrestamoPort.findByCondominioId(c.getId(), COUNT_ONE).totalElementos();
            totalAccesos += logAccesoPort.findByCondominioId(c.getId(), COUNT_ONE).totalElementos();
        }

        var condominiosRecientes = condominios.stream()
                .sorted(Comparator.comparing(CondominioModel::getFechaCreacion).reversed())
                .limit(4)
                .map(CondominioResumen::fromModel)
                .toList();

        var actividadPorCondominio = condominios.stream()
                .map(c -> {
                    long carritoOps = logPrestamoPort.findByCondominioId(c.getId(), COUNT_ONE).totalElementos();
                    long accesoOps = logAccesoPort.findByCondominioId(c.getId(), COUNT_ONE).totalElementos();
                    return new ActividadCondominio(c.getId(), c.getNombre(), carritoOps, accesoOps, carritoOps + accesoOps);
                })
                .sorted(Comparator.comparingLong(ActividadCondominio::totalOps).reversed())
                .limit(5)
                .toList();

        return new DashboardSuperAdminResponse(
                condominios.size(),
                totalUsuarios,
                totalTorres,
                totalPrestamos,
                totalAccesos,
                usuariosActivos,
                condominiosRecientes,
                actividadPorCondominio);
    }
}
