package com.condominios.sgc.application.port.out.model;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

import java.util.List;
import java.util.Optional;

public interface LogPrestamoCarritoRepository {
    Optional<LogPrestamoCarritoModel> buscarPorId(Long id);
    List<LogPrestamoCarritoModel> listarPorApartamento(Long idApartamento);
    List<LogPrestamoCarritoModel> listarActivosPorApartamento(Long idApartamento);
    LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel log);
    int contarActivosPorApartamento(Long idApartamento);
}
