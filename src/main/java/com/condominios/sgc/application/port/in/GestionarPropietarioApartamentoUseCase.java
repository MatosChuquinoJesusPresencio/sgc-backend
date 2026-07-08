package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.result.PropietarioApartamentoDetailResult;

public interface GestionarPropietarioApartamentoUseCase {
    PropietarioApartamentoDetailResult obtenerDetalle(Long condominioIdOverride);
}
