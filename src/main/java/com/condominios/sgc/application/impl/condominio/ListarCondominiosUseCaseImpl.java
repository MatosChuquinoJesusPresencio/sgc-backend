package com.condominios.sgc.application.impl.condominio;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.condominios.sgc.application.dto.query.ListarCondominiosQuery;
import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.application.usecase.condominio.ListarCondominiosUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.CondominioFilter;
import com.condominios.sgc.domain.model.CiudadModel;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.PaisModel;
import com.condominios.sgc.domain.port.CiudadPort;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.PaisPort;

public class ListarCondominiosUseCaseImpl implements ListarCondominiosUseCase {
    private final CondominioPort condominioPort;
    private final PaisPort paisPort;
    private final CiudadPort ciudadPort;

    public ListarCondominiosUseCaseImpl(CondominioPort condominioPort, PaisPort paisPort, CiudadPort ciudadPort) {
        this.condominioPort = condominioPort;
        this.paisPort = paisPort;
        this.ciudadPort = ciudadPort;
    }

    @Override
    public PaginacionResponse<CondominioResponse> ejecutar(ListarCondominiosQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());

        CondominioFilter filtro = new CondominioFilter(query.nombre(), query.idPais(), query.idCiudad());

        PaginacionResponse<CondominioModel> result = condominioPort.obtenerTodos(pagReq, filtro);

        Set<Long> paisIds = result.contenido().stream()
                .map(CondominioModel::getIdPais).collect(Collectors.toSet());
        Set<Long> ciudadIds = result.contenido().stream()
                .map(CondominioModel::getIdCiudad).collect(Collectors.toSet());

        Map<Long, String> paises = paisIds.stream()
                .collect(Collectors.toMap(Function.identity(),
                    id -> paisPort.obtenerPorId(id).map(PaisModel::getNombre).orElse("Desconocido")));
        Map<Long, String> ciudades = ciudadIds.stream()
                .collect(Collectors.toMap(Function.identity(),
                    id -> ciudadPort.obtenerPorId(id).map(CiudadModel::getNombre).orElse("Desconocido")));

        List<CondominioResponse> contenido = result.contenido().stream()
            .map(m -> CondominioResponse.desdeModelo(m,
                    paises.getOrDefault(m.getIdPais(), "Desconocido"),
                    ciudades.getOrDefault(m.getIdCiudad(), "Desconocido")))
            .toList();

        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
