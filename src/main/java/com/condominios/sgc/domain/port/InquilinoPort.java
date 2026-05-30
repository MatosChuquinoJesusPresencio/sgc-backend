package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.InquilinoModel;

public interface InquilinoPort {
    Optional<InquilinoModel> findById(Long id);
    PaginacionResponse<InquilinoModel> findByApartamentoId(Long apartamentoId, PaginacionRequest request);
    InquilinoModel save(InquilinoModel inquilino);
    void deleteById(Long id);
    Integer countByApartamentoId(Long apartamentoId);
}
