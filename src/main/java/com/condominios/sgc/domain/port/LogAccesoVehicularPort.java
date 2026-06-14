package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.LogAccesoVehicularFilter;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import java.util.List;
import java.util.Optional;

public interface LogAccesoVehicularPort {
    LogAccesoVehicularModel guardar(LogAccesoVehicularModel log);
    Optional<LogAccesoVehicularModel> obtenerPorId(Long id);
    List<LogAccesoVehicularModel> obtenerTodos();
    PaginacionResponse<LogAccesoVehicularModel> obtenerTodos(PaginacionRequest paginacion, LogAccesoVehicularFilter Filter);
    List<LogAccesoVehicularModel> obtenerPorVehiculo(Long idVehiculo);
    List<LogAccesoVehicularModel> obtenerPorPlaca(String placa);
    List<LogAccesoVehicularModel> obtenerSinSalida();
    void eliminarPorId(Long id);
}

