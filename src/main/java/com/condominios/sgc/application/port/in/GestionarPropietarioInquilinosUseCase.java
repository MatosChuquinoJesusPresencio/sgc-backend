package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.command.CrearPropietarioInquilinoCommand;
import com.condominios.sgc.application.dto.command.EditarPropietarioInquilinoCommand;
import com.condominios.sgc.application.dto.result.PropietarioInquilinoResult;

public interface GestionarPropietarioInquilinosUseCase {
    List<PropietarioInquilinoResult> listar(Long condominioIdOverride, Long apartamentoIdOverride);
    PropietarioInquilinoResult crear(Long condominioIdOverride, CrearPropietarioInquilinoCommand cmd);
    PropietarioInquilinoResult editar(Long condominioIdOverride, Long id, EditarPropietarioInquilinoCommand cmd);
    void eliminar(Long condominioIdOverride, Long id);
}
