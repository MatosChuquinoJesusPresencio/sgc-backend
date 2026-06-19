package com.condominios.sgc.application.port.out.model;

import com.condominios.sgc.domain.model.VehiculoModel;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository {
    Optional<VehiculoModel> buscarPorId(Long id);
    List<VehiculoModel> listarPorCondominio(Long idCondominio);
    List<VehiculoModel> listarPorPropietario(Long idPropietario);
    List<VehiculoModel> listarPorInquilino(Long idInquilino);
    VehiculoModel guardar(VehiculoModel vehiculo);
    void eliminar(Long id);
    int contarPorCondominio(Long idCondominio);
    int contarPorPropietario(Long idPropietario);
}
