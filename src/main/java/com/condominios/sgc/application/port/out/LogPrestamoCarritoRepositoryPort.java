package com.condominios.sgc.application.port.out;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public interface LogPrestamoCarritoRepositoryPort {
    Optional<LogPrestamoCarritoModel> buscarPorId(Long id);
    LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel modelo);
    void eliminarPorId(Long id);
    Page<LogPrestamoCarritoModel> buscarPorCondominio(Long idCondominio, Long userId, Instant fechaInicio, Instant fechaFin, Pageable pageable);
    List<LogPrestamoCarritoModel> buscarActivosPorCondominio(Long idCondominio);
}
