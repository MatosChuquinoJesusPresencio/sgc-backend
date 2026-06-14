package com.condominios.sgc.application.impl.estacionamiento;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarEstacionamientosQuery;
import com.condominios.sgc.application.dto.response.EstacionamientoResponse;
import com.condominios.sgc.application.usecase.estacionamiento.ListarEstacionamientosUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.EstacionamientoFilter;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ListarEstacionamientosUseCaseImpl implements ListarEstacionamientosUseCase {
    private final EstacionamientoPort estacionamientoPort;

    public ListarEstacionamientosUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public PaginacionResponse<EstacionamientoResponse> ejecutar(ListarEstacionamientosQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());

        EstacionamientoFilter filtro = new EstacionamientoFilter(
            query.numero(), query.tipoVehiculo(), query.disponible(),
            query.idApartamento(), query.idCondominio());

        PaginacionResponse<EstacionamientoModel> result = estacionamientoPort.obtenerTodos(pagReq, filtro);

        List<EstacionamientoResponse> contenido = result.contenido().stream()
            .map(EstacionamientoResponse::desdeModelo).toList();

        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
