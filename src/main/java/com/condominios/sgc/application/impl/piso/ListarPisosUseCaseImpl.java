package com.condominios.sgc.application.impl.piso;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarPisosQuery;
import com.condominios.sgc.application.dto.response.PisoResponse;
import com.condominios.sgc.application.usecase.piso.ListarPisosUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.PisoFilter;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.PisoPort;

public class ListarPisosUseCaseImpl implements ListarPisosUseCase {
    private final PisoPort pisoPort;

    public ListarPisosUseCaseImpl(PisoPort pisoPort) {
        this.pisoPort = pisoPort;
    }

    @Override
    public PaginacionResponse<PisoResponse> ejecutar(ListarPisosQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());
        PisoFilter filtro = new PisoFilter(query.numero(), query.idTorre());
        PaginacionResponse<PisoModel> result = pisoPort.obtenerTodos(pagReq, filtro);
        List<PisoResponse> contenido = result.contenido().stream()
            .map(PisoResponse::desdeModelo).toList();
        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
