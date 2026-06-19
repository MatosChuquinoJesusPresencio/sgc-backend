package com.condominios.sgc.application.port.out.model;

import com.condominios.sgc.domain.model.EstacionamientoModel;

import java.util.List;
import java.util.Optional;

public interface EstacionamientoRepository {
    Optional<EstacionamientoModel> buscarPorId(Long id);
    List<EstacionamientoModel> listarPorCondominio(Long idCondominio);
    List<EstacionamientoModel> listarDisponiblesPorCondominio(Long idCondominio);
    List<EstacionamientoModel> listarPorApartamento(Long idApartamento);
    EstacionamientoModel guardar(EstacionamientoModel estacionamiento);
    void eliminar(Long id);
    int contarPorCondominio(Long idCondominio);
}
