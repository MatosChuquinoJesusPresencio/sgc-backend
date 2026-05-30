package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public interface LogPrestamoCarritoPort {
    Optional<LogPrestamoCarritoModel> findById(Long id);
    PaginacionResponse<LogPrestamoCarritoModel> findByCondominioId(Long condominioId, PaginacionRequest request);
    PaginacionResponse<LogPrestamoCarritoModel> findByApartamentoId(Long apartamentoId, PaginacionRequest request);
    LogPrestamoCarritoModel save(LogPrestamoCarritoModel log);
}
