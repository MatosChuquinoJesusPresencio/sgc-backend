package com.condominios.sgc.application.port.out.model;

import com.condominios.sgc.domain.model.InquilinoModel;

import java.util.List;
import java.util.Optional;

public interface InquilinoRepository {
    Optional<InquilinoModel> buscarPorId(Long id);
    List<InquilinoModel> listarPorApartamento(Long idApartamento);
    InquilinoModel guardar(InquilinoModel inquilino);
    void eliminar(Long id);
    int contarPorApartamento(Long idApartamento);
}
