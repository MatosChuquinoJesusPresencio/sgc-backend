package com.condominios.sgc.application.impl.inquilino;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarInquilinosQuery;
import com.condominios.sgc.application.dto.response.InquilinoResponse;
import com.condominios.sgc.application.usecase.inquilino.ListarInquilinosUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.InquilinoFilter;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;

public class ListarInquilinosUseCaseImpl implements ListarInquilinosUseCase {
    private final InquilinoPort inquilinoPort;

    public ListarInquilinosUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public PaginacionResponse<InquilinoResponse> ejecutar(ListarInquilinosQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());

        InquilinoFilter filtro = new InquilinoFilter(
            query.nombres(), query.apellidos(), query.tipoDocumento(),
            query.numeroDocumento(), query.idApartamento());

        PaginacionResponse<InquilinoModel> result = inquilinoPort.obtenerTodos(pagReq, filtro);

        List<InquilinoResponse> contenido = result.contenido().stream()
            .map(InquilinoResponse::desdeModelo).toList();

        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
