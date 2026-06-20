package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.InquilinoModel;

public interface InquilinoRepositoryPort {
    Optional<InquilinoModel> buscarPorId(Long id);
    InquilinoModel guardar(InquilinoModel modelo);
    void eliminarPorId(Long id);
}
