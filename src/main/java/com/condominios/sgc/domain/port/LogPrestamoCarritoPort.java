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
    List<LogPrestamoCarritoModel> obtenerTodos();
    PaginacionResponse<LogPrestamoCarritoModel> obtenerTodos(PaginacionRequest paginacion, LogPrestamoCarritoFilter Filter);
    List<LogPrestamoCarritoModel> obtenerPorCarrito(Long idCarrito);
    List<LogPrestamoCarritoModel> obtenerPorApartamento(Long idApartamento);
    List<LogPrestamoCarritoModel> obtenerSinDevolucion();
    void eliminarPorId(Long id);
}

