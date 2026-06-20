package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.VehiculoModel;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepositoryPort {
    VehiculoModel guardar(VehiculoModel vehiculo);
    Optional<VehiculoModel> buscarPorId(Long id);
    Optional<VehiculoModel> buscarPorPlaca(String placa);
    List<VehiculoModel> listarPorCondominio(Long idCondominio);
    List<VehiculoModel> listarPorPropietario(Long idPropietario);
    List<VehiculoModel> listarPorInquilino(Long idInquilino);
    long contarPorCondominio(Long idCondominio);
    long contarPorPropietario(Long idPropietario);
    void eliminar(Long id);
}
