package com.condominios.sgc.application.impl.apartamento;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarApartamentosQuery;
import com.condominios.sgc.application.dto.response.ApartamentoResponse;
import com.condominios.sgc.application.usecase.apartamento.ListarApartamentosUsecase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.ApartamentoFilter;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class ListarApartamentosUsecaseImpl implements ListarApartamentosUsecase {
    private final ApartamentoPort apartamentoPort;

    public ListarApartamentosUsecaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public PaginacionResponse<ApartamentoResponse> ejecutar(ListarApartamentosQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());

        ApartamentoFilter filtro = new ApartamentoFilter(
            query.numero(), query.idPiso(), query.idPropietario(), query.derechoEstacionamiento());

        PaginacionResponse<ApartamentoModel> result = apartamentoPort.obtenerTodos(pagReq, filtro);

        List<ApartamentoResponse> contenido = result.contenido().stream()
            .map(ApartamentoResponse::desdeModelo).toList();
            
        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
