package com.condominios.sgc.application.usecase.pais;

import java.util.List;

import com.condominios.sgc.application.dto.response.PaisResponse;

public interface ListarPaisesUseCase {
    List<PaisResponse> ejecutar();
}
