package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.TorreModel;

public interface TorreRepositoryPort {
    Optional<TorreModel> buscarPorId(Long id);
    TorreModel guardar(TorreModel modelo);
    void eliminarPorId(Long id);
}
