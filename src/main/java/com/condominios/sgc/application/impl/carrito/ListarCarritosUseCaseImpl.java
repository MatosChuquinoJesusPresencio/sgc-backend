package com.condominios.sgc.application.impl.carrito;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarCarritosQuery;
import com.condominios.sgc.application.dto.response.CarritoResponse;
import com.condominios.sgc.application.usecase.carrito.ListarCarritosUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.CarritoFilter;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;

public class ListarCarritosUseCaseImpl implements ListarCarritosUseCase {
    private final CarritoPort carritoPort;

    public ListarCarritosUseCaseImpl(CarritoPort carritoPort) {
        this.carritoPort = carritoPort;
    }

    @Override
    public PaginacionResponse<CarritoResponse> ejecutar(ListarCarritosQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());

        CarritoFilter filtro = new CarritoFilter(query.codigo(), query.estado(), query.idCondominio());

        PaginacionResponse<CarritoModel> result = carritoPort.obtenerTodos(pagReq, filtro);

        List<CarritoResponse> contenido = result.contenido().stream()
            .map(CarritoResponse::desdeModelo).toList();
            
        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
