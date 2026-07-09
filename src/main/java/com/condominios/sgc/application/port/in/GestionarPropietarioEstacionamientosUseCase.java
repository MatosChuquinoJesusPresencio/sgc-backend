package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.result.PropietarioEstacionamientoResult;

public interface GestionarPropietarioEstacionamientosUseCase {
    List<PropietarioEstacionamientoResult> listar(Long condominioIdOverride);
}
