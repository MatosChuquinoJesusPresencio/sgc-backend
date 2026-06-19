package com.condominios.sgc.application.port.out.catalog;

import com.condominios.sgc.domain.catalog.Ciudad;

import java.util.List;
import java.util.Optional;

public interface CiudadRepository {
    Optional<Ciudad> buscarPorId(long id);
    List<Ciudad> listarPorPais(long idPais);
    List<Ciudad> listarTodos();
}
