package com.condominios.sgc.application.impl;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarVehiculosQuery;
import com.condominios.sgc.application.dto.response.VehiculoResponse;
import com.condominios.sgc.application.usecase.ListarVehiculosUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.VehiculoFilter;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ListarVehiculosUseCaseImpl implements ListarVehiculosUseCase {
    private final VehiculoPort vehiculoPort;

    public ListarVehiculosUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public PaginacionResponse<VehiculoResponse> ejecutar(ListarVehiculosQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());
        VehiculoFilter filtro = new VehiculoFilter(
            query.placa(), query.idPropietario(), query.idInquilino(), query.marca());
        PaginacionResponse<VehiculoModel> result = vehiculoPort.obtenerTodos(pagReq, filtro);
        List<VehiculoResponse> contenido = result.contenido().stream()
            .map(VehiculoResponse::desdeModelo).toList();
        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
