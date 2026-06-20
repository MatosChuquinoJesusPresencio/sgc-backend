package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.CondominioModel;

public interface CondominioRepositoryPort {
    Optional<CondominioModel> buscarPorId(Long id);
    CondominioModel guardar(CondominioModel modelo);
    void eliminarPorId(Long id);
}
