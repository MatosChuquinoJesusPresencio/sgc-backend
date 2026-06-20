package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.result.UsuarioActualResult;

public interface ObtenerUsuarioActualUseCase {
    UsuarioActualResult ejecutar(Long id);
}
