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
    List<EstacionamientoModel> obtenerTodos();
    PaginacionResponse<EstacionamientoModel> obtenerTodos(PaginacionRequest paginacion, EstacionamientoFilter Filter);
    List<EstacionamientoModel> obtenerPorCondominio(Long idCondominio);
    List<EstacionamientoModel> obtenerPorApartamento(Long idApartamento);
    List<EstacionamientoModel> obtenerDisponibles(Boolean disponible);
    void eliminarPorId(Long id);
}

