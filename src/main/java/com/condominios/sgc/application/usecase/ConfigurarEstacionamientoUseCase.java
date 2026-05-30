package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.model.EstacionamientoModel;

public interface ConfigurarEstacionamientoUseCase {
    EstacionamientoModel ejecutar(Long id, TipoVehiculo tipoVehiculo, Integer capacidadMaxima);
}
