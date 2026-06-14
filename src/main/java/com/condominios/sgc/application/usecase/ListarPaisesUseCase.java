package com.condominios.sgc.application.usecase;

import java.util.List;

import com.condominios.sgc.application.dto.response.PaisResponse;

public interface ListarPaisesUseCase {
    List<PaisResponse> ejecutar();
}
