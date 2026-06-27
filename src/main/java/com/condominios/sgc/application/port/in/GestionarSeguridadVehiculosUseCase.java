package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.result.SecurityVehicleVerificationResult;

public interface GestionarSeguridadVehiculosUseCase {
    SecurityVehicleVerificationResult verificarPorPlaca(String placa);
}
