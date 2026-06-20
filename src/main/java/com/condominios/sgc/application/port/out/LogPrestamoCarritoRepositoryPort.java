package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

import java.util.List;
import java.util.Optional;

public interface LogPrestamoCarritoRepositoryPort {
    LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel log);
    Optional<LogPrestamoCarritoModel> buscarPorId(Long id);
    List<LogPrestamoCarritoModel> listarPorApartamento(Long idApartamento);
    List<LogPrestamoCarritoModel> listarPorCarrito(Long idCarrito);
}
