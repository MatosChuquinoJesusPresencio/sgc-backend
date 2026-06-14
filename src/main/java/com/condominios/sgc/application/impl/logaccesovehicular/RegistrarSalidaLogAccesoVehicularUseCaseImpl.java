package com.condominios.sgc.application.impl.logaccesovehicular;

import com.condominios.sgc.application.dto.response.LogAccesoVehicularResponse;
import com.condominios.sgc.application.usecase.logaccesovehicular.RegistrarSalidaLogAccesoVehicularUseCase;
import com.condominios.sgc.domain.exception.LogAccesoVehicularException;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

public class RegistrarSalidaLogAccesoVehicularUseCaseImpl implements RegistrarSalidaLogAccesoVehicularUseCase {
    private final LogAccesoVehicularPort logAccesoVehicularPort;

    public RegistrarSalidaLogAccesoVehicularUseCaseImpl(LogAccesoVehicularPort logAccesoVehicularPort) {
        this.logAccesoVehicularPort = logAccesoVehicularPort;
    }

    @Override
    public LogAccesoVehicularResponse ejecutar(Long id) {
        LogAccesoVehicularModel log = logAccesoVehicularPort.obtenerPorId(id)
            .orElseThrow(LogAccesoVehicularException::noEncontrado);

        log.registrarSalida();

        log = logAccesoVehicularPort.guardar(log);

        return LogAccesoVehicularResponse.desdeModelo(log);
    }
}
