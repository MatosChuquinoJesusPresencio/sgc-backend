package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.EstacionamientoModel;

import java.util.List;
import java.util.Optional;

public interface EstacionamientoRepositoryPort {
    EstacionamientoModel guardar(EstacionamientoModel estacionamiento);
    Optional<EstacionamientoModel> buscarPorId(Long id);
    List<EstacionamientoModel> listarPorCondominio(Long idCondominio);
    List<EstacionamientoModel> listarPorApartamento(Long idApartamento);
    long contarPorCondominio(Long idCondominio);
}
