package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.EstacionamientoModel;

public interface EstacionamientoRepositoryPort {
    Optional<EstacionamientoModel> buscarPorId(Long id);
    EstacionamientoModel guardar(EstacionamientoModel modelo);
    void eliminarPorId(Long id);
}
