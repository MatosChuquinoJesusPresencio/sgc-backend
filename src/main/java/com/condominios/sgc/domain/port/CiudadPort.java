package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.CiudadFilter;
import com.condominios.sgc.domain.model.CiudadModel;
import java.util.List;
import java.util.Optional;

public interface CiudadPort {
    CiudadModel guardar(CiudadModel ciudad);
    Optional<CiudadModel> obtenerPorId(Long id);
    List<CiudadModel> obtenerTodos();
    PaginacionResponse<CiudadModel> obtenerTodos(PaginacionRequest paginacion, CiudadFilter Filter);
    List<CiudadModel> obtenerPorPais(Long idPais);
    void eliminarPorId(Long id);
}

