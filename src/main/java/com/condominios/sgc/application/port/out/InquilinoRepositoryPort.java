package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.InquilinoModel;

import java.util.List;
import java.util.Optional;

public interface InquilinoRepositoryPort {
    InquilinoModel guardar(InquilinoModel inquilino);
    Optional<InquilinoModel> buscarPorId(Long id);
    List<InquilinoModel> listarPorApartamento(Long idApartamento);
    long contarPorApartamento(Long idApartamento);
    void eliminar(Long id);
}
