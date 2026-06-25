package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.result.SecurityParkingSlotResult;

public interface GestionarSeguridadEstacionamientosUseCase {
    List<SecurityParkingSlotResult> listarSlots();
}
