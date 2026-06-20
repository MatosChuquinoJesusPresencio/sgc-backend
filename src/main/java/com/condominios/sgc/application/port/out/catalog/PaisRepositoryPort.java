package com.condominios.sgc.application.port.out.catalog;

import java.util.Optional;

import com.condominios.sgc.domain.model.catalog.Pais;

public interface PaisRepositoryPort {
    Optional<Pais> buscarPorId(Long id);
    Pais guardar(Pais pais);
    void eliminarPorId(Long id);
}
