package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerCondominioRelationsUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.port.CarritoPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.web.dto.CondominioRelationsResponse;

public class ObtenerCondominioRelationsUseCaseImpl implements ObtenerCondominioRelationsUseCase {

    private static final PaginacionRequest COUNT_ONE = new PaginacionRequest(0, 1, null, null, null);

    private final TorrePort torrePort;
    private final UsuarioPort usuarioPort;
    private final CarritoPort carritoPort;
    private final ConfiguracionPort configuracionPort;

    public ObtenerCondominioRelationsUseCaseImpl(
            TorrePort torrePort,
            UsuarioPort usuarioPort,
            CarritoPort carritoPort,
            ConfiguracionPort configuracionPort) {
        this.torrePort = torrePort;
        this.usuarioPort = usuarioPort;
        this.carritoPort = carritoPort;
        this.configuracionPort = configuracionPort;
    }

    @Override
    public CondominioRelationsResponse ejecutar(Long condominioId) {
        long torres = torrePort.findByCondominioId(condominioId, COUNT_ONE).totalElementos();

        var usuariosPage = usuarioPort.findAll(new PaginacionRequest(0, 1000, null, null, null));
        long usuarios = usuariosPage.contenido().stream()
                .filter(u -> condominioId.equals(u.getCondominioId()))
                .count();

        long carritos = carritoPort.findByCondominioId(condominioId, COUNT_ONE).totalElementos();

        var configOpt = configuracionPort.findByCondominioId(condominioId);
        long configuraciones = configOpt.isPresent() ? 1 : 0;
        CondominioRelationsResponse.ConfigData config = configOpt
            .map(c -> new CondominioRelationsResponse.ConfigData(
                c.getMaxAutos(),
                c.getMaxMotos(),
                c.getPenalizacionPorMin(),
                c.getMaxTiempoPrestamoMin()
            ))
            .orElse(null);

        return new CondominioRelationsResponse(torres, usuarios, carritos, configuraciones, config);
    }
}
