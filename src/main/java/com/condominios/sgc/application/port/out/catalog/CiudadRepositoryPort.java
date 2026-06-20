package com.condominios.sgc.application.port.out.catalog;

import com.condominios.sgc.domain.model.catalog.Ciudad;

import java.util.List;
import java.util.Optional;

public interface CiudadRepositoryPort {
    Optional<Ciudad> buscarPorId(Long id);
    List<Ciudad> listarPorPais(Long idPais);
    List<Ciudad> listarTodos();
}
