package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.command.CrearPropietarioInquilinoCommand;
import com.condominios.sgc.application.dto.result.PropietarioInquilinoResult;

public interface GestionarPropietarioInquilinosUseCase {
    List<PropietarioInquilinoResult> listar();
    PropietarioInquilinoResult crear(CrearPropietarioInquilinoCommand cmd);
    void eliminar(Long id);
}
