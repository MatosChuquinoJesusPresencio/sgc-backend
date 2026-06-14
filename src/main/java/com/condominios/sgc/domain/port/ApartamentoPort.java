package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.ApartamentoFilter;
import com.condominios.sgc.domain.model.ApartamentoModel;
import java.util.Optional;

public interface ApartamentoPort {
    ApartamentoModel guardar(ApartamentoModel apartamento);
    Optional<ApartamentoModel> obtenerPorId(Long id);
    PaginacionResponse<ApartamentoModel> obtenerTodos(PaginacionRequest request, ApartamentoFilter filtro);
    void eliminarPorId(Long id);
}

