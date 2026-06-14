package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.ApartamentoFilter;
import com.condominios.sgc.domain.model.ApartamentoModel;
import java.util.List;
import java.util.Optional;

public interface ApartamentoPort {
    ApartamentoModel guardar(ApartamentoModel apartamento);
    Optional<ApartamentoModel> obtenerPorId(Long id);
    List<ApartamentoModel> obtenerTodos();
    PaginacionResponse<ApartamentoModel> obtenerTodos(PaginacionRequest paginacion, ApartamentoFilter Filter);
    List<ApartamentoModel> obtenerPorPiso(Long idPiso);
    List<ApartamentoModel> obtenerPorPropietario(Long idPropietario);
    void eliminarPorId(Long id);
}

