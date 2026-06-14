package com.condominios.sgc.application.usecase.ciudad;

import java.util.List;
import com.condominios.sgc.application.dto.response.CiudadResponse;

public interface ListarCiudadesPorPaisUseCase {
    List<CiudadResponse> ejecutar(Long idPais);
}
