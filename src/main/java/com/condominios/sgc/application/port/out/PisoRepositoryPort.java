package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.PisoModel;

public interface PisoRepositoryPort {
    Optional<PisoModel> buscarPorId(Long id);
    PisoModel guardar(PisoModel modelo);
    void eliminarPorId(Long id);
}
