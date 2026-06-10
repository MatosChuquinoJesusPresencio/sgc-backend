package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.ApartamentoModel;

public interface ApartamentoPort {
    Optional<ApartamentoModel> findById(Long id);
    PaginacionResponse<ApartamentoModel> findByPisoId(Long pisoId, PaginacionRequest pageRequest);
    ApartamentoModel save(ApartamentoModel apartamento);
    void deleteById(Long id);
    Optional<ApartamentoModel> findByPropietarioId(Long propietarioId);
    PaginacionResponse<ApartamentoModel> findByFiltros(Long condominioId, Long torreId, Long pisoId, PaginacionRequest pageRequest);
}
