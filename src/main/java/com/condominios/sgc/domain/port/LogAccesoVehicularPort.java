package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.filter.LogAccesoVehicularFilter;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;

import java.util.Optional;

public interface LogAccesoVehicularPort {
    LogAccesoVehicularModel guardar(LogAccesoVehicularModel log);
    Optional<LogAccesoVehicularModel> obtenerPorId(Long id);
    Pagina<LogAccesoVehicularModel> obtenerTodos(Paginable request, LogAccesoVehicularFilter filtro);
}

