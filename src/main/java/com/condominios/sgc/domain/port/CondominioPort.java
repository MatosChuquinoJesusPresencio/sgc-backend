package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CondominioModel;

public interface CondominioPort {
    Optional<CondominioModel> findById(Long id);
    PaginacionResponse<CondominioModel> findAll(PaginacionRequest request);
    CondominioModel save(CondominioModel model);
    void deleteById(Long id);
    boolean existsByNombre(String nombre);
}
