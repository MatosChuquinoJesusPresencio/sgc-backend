package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.ApartamentoModel;

public interface ApartamentoRepositoryPort {
    Optional<ApartamentoModel> buscarPorId(Long id);
    ApartamentoModel guardar(ApartamentoModel modelo);
    void eliminarPorId(Long id);
    Optional<ApartamentoModel> buscarPorPropietario(Long idPropietario);
    Optional<ApartamentoModel> buscarPorNumeroYCondominio(Integer numero, Long idCondominio);
}
