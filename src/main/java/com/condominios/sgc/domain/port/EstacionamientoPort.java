package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.EstacionamientoFilter;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import java.util.List;
import java.util.Optional;

public interface EstacionamientoPort {
    EstacionamientoModel guardar(EstacionamientoModel estacionamiento);
    Optional<EstacionamientoModel> obtenerPorId(Long id);
    PaginacionResponse<EstacionamientoModel> obtenerTodos(PaginacionRequest request, EstacionamientoFilter filtro);
    List<EstacionamientoModel> obtenerPorApartamento(Long idApartamento);
    void eliminarPorId(Long id);
}

