package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.filter.LogPrestamoCarritoFilter;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;

import java.util.List;
import java.util.Optional;

public interface LogPrestamoCarritoPort {
    LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel log);
    Optional<LogPrestamoCarritoModel> obtenerPorId(Long id);
    Pagina<LogPrestamoCarritoModel> obtenerTodos(Paginable request, LogPrestamoCarritoFilter filtro);
    List<LogPrestamoCarritoModel> obtenerSinDevolucion();
    long contarSinDevolucionPorApartamento(Long idApartamento);
}

