package com.condominios.sgc.application.port.out.model;

import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.shared.value_objects.PlacaVehiculo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LogAccesoVehicularRepository {
    Optional<LogAccesoVehicularModel> buscarPorId(Long id);
    List<LogAccesoVehicularModel> listarPorPlaca(PlacaVehiculo placa);
    List<LogAccesoVehicularModel> listarPorFecha(LocalDateTime desde, LocalDateTime hasta);
    LogAccesoVehicularModel guardar(LogAccesoVehicularModel log);
}
