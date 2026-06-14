package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.TorreFilter;
import com.condominios.sgc.domain.model.TorreModel;
import java.util.List;
import java.util.Optional;

public interface TorrePort {
    TorreModel guardar(TorreModel torre);
    Optional<TorreModel> obtenerPorId(Long id);
    List<TorreModel> obtenerTodos();
    PaginacionResponse<TorreModel> obtenerTodos(PaginacionRequest request, TorreFilter filtro);
    List<TorreModel> obtenerPorCondominio(Long idCondominio);
    void eliminarPorId(Long id);
}

