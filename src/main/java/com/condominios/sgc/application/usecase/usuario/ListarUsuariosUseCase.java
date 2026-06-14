package com.condominios.sgc.application.usecase.usuario;

import com.condominios.sgc.application.dto.query.ListarUsuariosQuery;
import com.condominios.sgc.application.dto.response.UsuarioResponse;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;

public interface ListarUsuariosUseCase {
    PaginacionResponse<UsuarioResponse> ejecutar(ListarUsuariosQuery query);
}
