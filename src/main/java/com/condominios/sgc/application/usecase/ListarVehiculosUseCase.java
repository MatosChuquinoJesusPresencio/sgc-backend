package com.condominios.sgc.application.usecase;

import java.util.List;
import com.condominios.sgc.application.dto.VehiculoResponse;

public interface ListarVehiculosUseCase {
    List<VehiculoResponse> listarTodos();
}
