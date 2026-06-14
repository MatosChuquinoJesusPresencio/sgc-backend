package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.CrearCarritoCommand;
import com.condominios.sgc.application.dto.response.CarritoResponse;
import com.condominios.sgc.application.usecase.CrearCarritoUseCase;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;

public class CrearCarritoUseCaseImpl implements CrearCarritoUseCase {
    private final CarritoPort carritoPort;

    public CrearCarritoUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public CarritoResponse ejecutar(CrearCarritoCommand command) {
        CarritoModel carrito = new CarritoModel(command.codigo(), command.idCondominio());
        
        carrito = carritoPort.guardar(carrito);

        return CarritoResponse.desdeModelo(carrito);
    }
}
