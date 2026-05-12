package com.condominios.sgc.domain.port;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.domain.model.VehiculoModel;

public interface VehiculoPort {
    Optional<VehiculoModel> findById(Long id);
    List<VehiculoModel> findAll();
    List<VehiculoModel> findByPropietarioInquilinoId(Long inquilinoId);
    List<VehiculoModel> findByPropietarioUsuarioId(String usuarioId);
    VehiculoModel save(VehiculoModel vehiculo);
    void deleteById(Long id);
}
