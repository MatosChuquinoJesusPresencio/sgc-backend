package com.condominios.sgc.application.port.out;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.domain.model.VehiculoModel;

public interface VehiculoRepositoryPort {
    Optional<VehiculoModel> buscarPorId(Long id);
    VehiculoModel guardar(VehiculoModel modelo);
    void eliminarPorId(Long id);
    long contarPorCondominio(Long idCondominio);
    List<VehiculoModel> buscarPorPropietario(Long idPropietario);
    List<VehiculoModel> buscarPorInquilino(Long idInquilino);
    List<VehiculoModel> buscarPorCondominio(Long idCondominio);
    Optional<VehiculoModel> buscarPorPlaca(String placa);
    void eliminarPorInquilino(Long idInquilino);
    List<VehiculoModel> buscarPorIdEstacionamiento(Long idEstacionamiento);
    List<VehiculoModel> buscarSinEstacionamiento(Long idCondominio);
}
