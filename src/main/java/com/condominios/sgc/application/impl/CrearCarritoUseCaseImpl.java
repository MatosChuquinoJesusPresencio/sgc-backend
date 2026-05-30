package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearCarritoRequest;
import com.condominios.sgc.application.usecase.CrearCarritoUseCase;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;

public class CrearCarritoUseCaseImpl implements CrearCarritoUseCase {

    private final CarritoPort carritoPort;

    public CrearCarritoUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public CarritoModel ejecutar(CrearCarritoRequest request) {
        CarritoModel modelo = new CarritoModel(
                request.codigo(),
                request.estadoInicial(),
                request.condominioId());

        return carritoPort.save(modelo);
    }
}
