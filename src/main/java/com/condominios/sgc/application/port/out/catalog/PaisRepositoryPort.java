package com.condominios.sgc.application.port.out.catalog;

import com.condominios.sgc.domain.model.catalog.Pais;

import java.util.List;
import java.util.Optional;

public interface PaisRepositoryPort {
    Optional<Pais> buscarPorId(Long id);
    List<Pais> listarTodos();
}
