package com.condominios.sgc.application.port.out.catalog;

import java.util.Optional;

import com.condominios.sgc.domain.model.catalog.CiudadModel;

public interface CiudadRepositoryPort {
    Optional<CiudadModel> buscarPorId(Long id);
    CiudadModel guardar(CiudadModel modelo);
    void eliminarPorId(Long id);
}
