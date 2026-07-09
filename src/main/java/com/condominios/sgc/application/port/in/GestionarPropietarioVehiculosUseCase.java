package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.command.CrearPropietarioVehiculoCommand;
import com.condominios.sgc.application.dto.result.PropietarioVehiculoResult;

public interface GestionarPropietarioVehiculosUseCase {
    List<PropietarioVehiculoResult> listar();
    PropietarioVehiculoResult crear(Long condominioIdOverride, CrearPropietarioVehiculoCommand cmd);
    PropietarioVehiculoResult asignarEstacionamiento(Long condominioIdOverride, Long vehiculoId, Long idEstacionamiento);
    void eliminar(Long id);
}
