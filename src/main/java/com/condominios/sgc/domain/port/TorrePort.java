package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.TorreModel;

public interface TorrePort {
    Optional<TorreModel> findById(Long id);
    PaginacionResponse<TorreModel> findByCondominioId(Long condominioId, PaginacionRequest pageRequest);
    TorreModel save(TorreModel torre);
    void deleteById(Long id);
}
