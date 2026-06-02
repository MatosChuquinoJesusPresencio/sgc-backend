package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.EstacionamientoModel;

public interface EstacionamientoPort {
    Optional<EstacionamientoModel> findById(Long id);
    PaginacionResponse<EstacionamientoModel> findByApartamentoId(Long apartamentoId, PaginacionRequest request);
    PaginacionResponse<EstacionamientoModel> findByCondominioId(Long condominioId, PaginacionRequest request);
    EstacionamientoModel save(EstacionamientoModel estacionamiento);
    void deleteById(Long id);
}
