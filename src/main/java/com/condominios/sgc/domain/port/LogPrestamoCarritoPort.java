package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.LogPrestamoCarritoFilter;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import java.util.List;
import java.util.Optional;

public interface LogPrestamoCarritoPort {
    LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel log);
    Optional<LogPrestamoCarritoModel> obtenerPorId(Long id);
    PaginacionResponse<LogPrestamoCarritoModel> obtenerTodos(PaginacionRequest request, LogPrestamoCarritoFilter filtro);
    List<LogPrestamoCarritoModel> obtenerSinDevolucion();
    long contarSinDevolucionPorApartamento(Long idApartamento);
}

