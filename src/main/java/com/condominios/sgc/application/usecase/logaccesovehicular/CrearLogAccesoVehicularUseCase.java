package com.condominios.sgc.application.usecase.logaccesovehicular;

import com.condominios.sgc.application.dto.command.CrearLogAccesoVehicularCommand;
import com.condominios.sgc.application.dto.response.LogAccesoVehicularResponse;

public interface CrearLogAccesoVehicularUseCase {
    LogAccesoVehicularResponse ejecutar(CrearLogAccesoVehicularCommand command);
}
