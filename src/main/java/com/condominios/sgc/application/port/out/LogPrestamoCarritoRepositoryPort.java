package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public interface LogPrestamoCarritoRepositoryPort {
    Optional<LogPrestamoCarritoModel> buscarPorId(Long id);
    LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel modelo);
    void eliminarPorId(Long id);
}
