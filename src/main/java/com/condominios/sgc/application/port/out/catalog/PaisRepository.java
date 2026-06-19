package com.condominios.sgc.application.port.out.catalog;

import com.condominios.sgc.domain.catalog.Pais;

import java.util.List;
import java.util.Optional;

public interface PaisRepository {
    Optional<Pais> buscarPorId(long id);
    List<Pais> listarTodos();
}
