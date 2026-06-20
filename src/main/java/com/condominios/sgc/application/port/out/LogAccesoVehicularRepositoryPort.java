package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public interface LogAccesoVehicularRepositoryPort {
    Optional<LogAccesoVehicularModel> buscarPorId(Long id);
    LogAccesoVehicularModel guardar(LogAccesoVehicularModel modelo);
    void eliminarPorId(Long id);
}
