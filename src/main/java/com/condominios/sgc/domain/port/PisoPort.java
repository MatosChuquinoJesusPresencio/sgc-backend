package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.PisoFilter;
import com.condominios.sgc.domain.model.PisoModel;
import java.util.List;
import java.util.Optional;

public interface PisoPort {
    PisoModel guardar(PisoModel piso);
    Optional<PisoModel> obtenerPorId(Long id);
    List<PisoModel> obtenerTodos();
    PaginacionResponse<PisoModel> obtenerTodos(PaginacionRequest request, PisoFilter filtro);
    List<PisoModel> obtenerPorTorre(Long idTorre);
    int contarPorTorre(Long idTorre);
    void eliminarPorId(Long id);
}

