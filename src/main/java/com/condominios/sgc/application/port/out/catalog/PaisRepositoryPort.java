package com.condominios.sgc.application.port.out.catalog;

import java.util.Optional;

import com.condominios.sgc.domain.model.catalog.PaisModel;

public interface PaisRepositoryPort {
    Optional<PaisModel> buscarPorId(Long id);
    PaisModel guardar(PaisModel modelo);
    void eliminarPorId(Long id);
}
