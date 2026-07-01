package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.domain.model.EstacionamientoModel;

public interface EstacionamientoRepositoryPort {
    Optional<EstacionamientoModel> buscarPorId(Long id);
    EstacionamientoModel guardar(EstacionamientoModel modelo);
    void eliminarPorId(Long id);
    long contarPorApartamento(Long idApartamento);
    PaginaResult<EstacionamientoModel> buscarPorCondominio(Long idCondominio, PaginaQuery paginacion);
}
