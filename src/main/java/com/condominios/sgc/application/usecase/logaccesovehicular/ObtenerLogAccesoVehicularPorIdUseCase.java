package com.condominios.sgc.application.usecase.logaccesovehicular;

import com.condominios.sgc.application.dto.response.LogAccesoVehicularResponse;

public interface ObtenerLogAccesoVehicularPorIdUseCase {
    LogAccesoVehicularResponse ejecutar(Long id);
}
