package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.CondominioFilter;
import com.condominios.sgc.domain.model.CondominioModel;
import java.util.List;
import java.util.Optional;

public interface CondominioPort {
    CondominioModel guardar(CondominioModel condominio);
    Optional<CondominioModel> obtenerPorId(Long id);
    List<CondominioModel> obtenerTodos();
    PaginacionResponse<CondominioModel> obtenerTodos(PaginacionRequest paginacion, CondominioFilter Filter);
    void eliminarPorId(Long id);
}

