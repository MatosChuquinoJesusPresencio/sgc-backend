package com.condominios.sgc.application.port.out.catalog;

import java.util.Optional;

import com.condominios.sgc.domain.model.catalog.MonedaModel;

public interface MonedaRepositoryPort {
    Optional<MonedaModel> buscarPorId(Long id);
    MonedaModel guardar(MonedaModel modelo);
    void eliminarPorId(Long id);
}
