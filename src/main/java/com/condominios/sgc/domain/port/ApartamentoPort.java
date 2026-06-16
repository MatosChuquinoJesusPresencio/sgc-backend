package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.ApartamentoFilter;
import com.condominios.sgc.domain.model.ApartamentoModel;
import java.util.Optional;

public interface ApartamentoPort {
    ApartamentoModel guardar(ApartamentoModel apartamento);
    Optional<ApartamentoModel> obtenerPorId(Long id);
    Optional<ApartamentoModel> obtenerPorPropietario(Long idPropietario);
    PaginacionResponse<ApartamentoModel> obtenerTodos(PaginacionRequest request, ApartamentoFilter filtro);
    int contarPorCondominio(Long idCondominio);
    void eliminarPorId(Long id);
}

