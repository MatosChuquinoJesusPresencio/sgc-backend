package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.result.SecurityUnassignedVehicleResult;
import com.condominios.sgc.application.dto.result.SecurityVehicleVerificationResult;

public interface GestionarSeguridadVehiculosUseCase {
    SecurityVehicleVerificationResult verificarPorPlaca(String placa);
    List<SecurityUnassignedVehicleResult> listarSinEstacionamiento(Long condominioId);
}
