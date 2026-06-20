package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.VehiculoModel;

public interface VehiculoRepositoryPort {
    Optional<VehiculoModel> buscarPorId(Long id);
    VehiculoModel guardar(VehiculoModel modelo);
    void eliminarPorId(Long id);
}
