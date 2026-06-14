package com.condominios.sgc.application.impl.logaccesovehicular;

import com.condominios.sgc.application.dto.command.CrearLogAccesoVehicularCommand;
import com.condominios.sgc.application.dto.response.LogAccesoVehicularResponse;
import com.condominios.sgc.application.usecase.logaccesovehicular.CrearLogAccesoVehicularUseCase;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;

public class CrearLogAccesoVehicularUseCaseImpl implements CrearLogAccesoVehicularUseCase {
    private final LogAccesoVehicularPort logAccesoVehicularPort;

    public CrearLogAccesoVehicularUseCaseImpl(LogAccesoVehicularPort logAccesoVehicularPort) {
        this.logAccesoVehicularPort = logAccesoVehicularPort;
    }

    @Override
    public LogAccesoVehicularResponse ejecutar(CrearLogAccesoVehicularCommand command) {
        LogAccesoVehicularModel log = new LogAccesoVehicularModel(
            command.placa(), command.ocupante(), command.datosInquilino(),
            command.metodo(), command.idVehiculo(), command.idEstacionamiento());

        log = logAccesoVehicularPort.guardar(log);

        return LogAccesoVehicularResponse.desdeModelo(log);
    }
}
