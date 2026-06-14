package com.condominios.sgc.application.usecase.usuario;

import com.condominios.sgc.application.dto.response.UsuarioResponse;

public interface ObtenerUsuarioPorCorreoUseCase {
    UsuarioResponse ejecutar(String correo);
}
