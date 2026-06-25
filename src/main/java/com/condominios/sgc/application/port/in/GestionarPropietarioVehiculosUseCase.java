package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.command.CrearPropietarioVehiculoCommand;
import com.condominios.sgc.application.dto.result.PropietarioVehiculoResult;

public interface GestionarPropietarioVehiculosUseCase {
    List<PropietarioVehiculoResult> listar();
    PropietarioVehiculoResult crear(CrearPropietarioVehiculoCommand cmd);
    void eliminar(Long id);
}
