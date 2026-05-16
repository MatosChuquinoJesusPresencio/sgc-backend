package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.UsuarioModel;

public interface ListarUsuariosUseCase {
    PaginacionResponse<UsuarioModel> ejecutar(PaginacionRequest request);
}
