package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.model.CiudadModel;
import java.util.List;
import java.util.Optional;

public interface CiudadPort {
    Optional<CiudadModel> obtenerPorId(Long id);
    List<CiudadModel> obtenerTodos();
    List<CiudadModel> obtenerPorPais(Long idPais);
}

