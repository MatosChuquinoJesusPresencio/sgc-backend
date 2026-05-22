package com.condominios.sgc.domain.port;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.domain.model.InquilinoModel;

public interface InquilinoPort {
    Optional<InquilinoModel> findById(Long id);
    List<InquilinoModel> findByApartamentoId(Long apartamentoId);
    InquilinoModel save(InquilinoModel inquilino);
    void deleteById(Long id);
    Integer countByApartamentoId(Long apartamentoId);
}
