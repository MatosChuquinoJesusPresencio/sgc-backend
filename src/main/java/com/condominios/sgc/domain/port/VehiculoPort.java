package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.VehiculoModel;

public interface VehiculoPort {
    Optional<VehiculoModel> findById(Long id);
    PaginacionResponse<VehiculoModel> findAll(PaginacionRequest request);
    PaginacionResponse<VehiculoModel> findByInquilinoId(Long inquilinoId, PaginacionRequest request);
    PaginacionResponse<VehiculoModel> findByPropietarioId(String propietarioId, PaginacionRequest request);
    VehiculoModel save(VehiculoModel vehiculo);
    void deleteById(Long id);
}
