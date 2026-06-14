package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.UsuarioResponse;

public interface CambiarEstadoActivoUsuarioUseCase {
    UsuarioResponse ejecutar(Long id, boolean activo);
}
