package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.result.CiudadResult;
import com.condominios.sgc.application.dto.result.PaisResult;

public interface CatalogoUseCase {
    List<PaisResult> listarPaises();
    List<CiudadResult> listarCiudadesPorPais(Long paisId);
}
