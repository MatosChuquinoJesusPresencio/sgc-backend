package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.CiudadModel;

public interface CiudadRepositoryPort {
    Optional<CiudadModel> buscarPorId(Long id);
    CiudadModel guardar(CiudadModel modelo);
    void eliminarPorId(Long id);
}
