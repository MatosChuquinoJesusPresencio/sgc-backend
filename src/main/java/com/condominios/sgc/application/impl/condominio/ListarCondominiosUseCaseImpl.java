package com.condominios.sgc.application.impl.condominio;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarCondominiosQuery;
import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.application.usecase.condominio.ListarCondominiosUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.CondominioFilter;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;

public class ListarCondominiosUseCaseImpl implements ListarCondominiosUseCase {
    private final CondominioPort condominioPort;

    public ListarCondominiosUseCaseImpl(CondominioPort condominioPort) {
        this.condominioPort = condominioPort;
    }

    @Override
    public PaginacionResponse<CondominioResponse> ejecutar(ListarCondominiosQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());

        CondominioFilter filtro = new CondominioFilter(query.nombre(), query.idPais(), query.idCiudad());

        PaginacionResponse<CondominioModel> result = condominioPort.obtenerTodos(pagReq, filtro);

        List<CondominioResponse> contenido = result.contenido().stream()
            .map(CondominioResponse::desdeModelo).toList();
            
        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
