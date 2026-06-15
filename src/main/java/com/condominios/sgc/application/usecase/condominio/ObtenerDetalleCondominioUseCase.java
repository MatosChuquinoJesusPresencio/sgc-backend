package com.condominios.sgc.application.usecase.condominio;

import com.condominios.sgc.application.dto.response.DetalleCondominioResponse;

public interface ObtenerDetalleCondominioUseCase {
    DetalleCondominioResponse ejecutar(Long id);
}