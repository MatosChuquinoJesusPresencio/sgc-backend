package com.condominios.sgc.application.impl;

import java.util.List;

import com.condominios.sgc.application.dto.query.ListarUsuariosQuery;
import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.application.usecase.ListarUsuariosUseCase;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.UsuarioFilter;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ListarUsuariosUseCaseImpl implements ListarUsuariosUseCase {
    private final UsuarioPort usuarioPort;

    public ListarUsuariosUseCaseImpl(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Override
    public PaginacionResponse<UsuarioResponse> ejecutar(ListarUsuariosQuery query) {
        PaginacionRequest pagReq = new PaginacionRequest(query.pagina(), query.tamano());
        UsuarioFilter filtro = new UsuarioFilter(
            query.nombres(), query.apellidos(), query.correo(),
            query.rol(), query.activo(), query.idCondominio());
        PaginacionResponse<UsuarioModel> result = usuarioPort.obtenerTodos(pagReq, filtro);
        List<UsuarioResponse> contenido = result.contenido().stream()
            .map(UsuarioResponse::desdeModelo).toList();
        return new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
            result.totalElementos(), result.totalPaginas());
    }
}
