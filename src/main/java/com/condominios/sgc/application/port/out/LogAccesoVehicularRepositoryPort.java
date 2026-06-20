package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

import java.util.List;
import java.util.Optional;

public interface LogAccesoVehicularRepositoryPort {
    LogAccesoVehicularModel guardar(LogAccesoVehicularModel log);
    Optional<LogAccesoVehicularModel> buscarPorId(Long id);
    List<LogAccesoVehicularModel> buscarPorPlaca(String placa);
    List<LogAccesoVehicularModel> listarPorVehiculo(Long idVehiculo);
    List<LogAccesoVehicularModel> listarPorEstacionamiento(Long idEstacionamiento);
}
