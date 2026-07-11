package com.condominios.sgc.application.port.out;

import java.time.Instant;
import java.util.Optional;

import java.util.List;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public interface LogPrestamoCarritoRepositoryPort {
    Optional<LogPrestamoCarritoModel> buscarPorId(Long id);
    LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel modelo);
    void eliminarPorId(Long id);
    PaginaResult<LogPrestamoCarritoModel> buscarPorCondominio(Long idCondominio, Long userId, Instant fechaInicio, Instant fechaFin, PaginaQuery paginacion);
    List<LogPrestamoCarritoModel> buscarActivosPorCondominio(Long idCondominio);
    List<LogPrestamoCarritoModel> listarTodosPorCondominio(Long idCondominio);
}
