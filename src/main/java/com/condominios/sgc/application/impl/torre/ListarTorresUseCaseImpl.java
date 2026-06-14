package com.condominios.sgc.application.impl.torre;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarTorresQuery;
import com.condominios.sgc.application.dto.response.TorreResponse;
import com.condominios.sgc.application.usecase.torre.ListarTorresUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.TorreFilter;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.TorrePort;

public class ListarTorresUseCaseImpl implements ListarTorresUseCase {
    private final TorrePort torrePort;

    public ListarTorresUseCaseImpl(TorrePort torrePort) {
        this.torrePort = torrePort;
    }

    @Override
    public PaginacionResponse<TorreResponse> ejecutar(ListarTorresQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());
        TorreFilter filtro = new TorreFilter(query.nombre(), query.idCondominio());
        PaginacionResponse<TorreModel> result = torrePort.obtenerTodos(pagReq, filtro);
        List<TorreResponse> contenido = result.contenido().stream()
            .map(TorreResponse::desdeModelo).toList();
        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
