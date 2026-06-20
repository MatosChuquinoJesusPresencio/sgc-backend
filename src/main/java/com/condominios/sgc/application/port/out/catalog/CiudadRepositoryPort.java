package com.condominios.sgc.application.port.out.catalog;

import java.util.Optional;

import com.condominios.sgc.domain.model.catalog.Ciudad;

public interface CiudadRepositoryPort {
    Optional<Ciudad> buscarPorId(Long id);
    Ciudad guardar(Ciudad ciudad);
    void eliminarPorId(Long id);
}
