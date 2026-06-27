package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.command.ActualizarAdministradorCommand;
import com.condominios.sgc.application.dto.command.CrearAdministradorCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdministradorResult;
import com.condominios.sgc.application.dto.result.CondominioSimpleResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarAdministradorUseCase {
    PaginaResult<AdministradorResult> listar(String search, Boolean activo, PaginaQuery query);
    AdministradorResult crear(CrearAdministradorCommand cmd);
    AdministradorResult actualizar(Long id, ActualizarAdministradorCommand cmd);
    void eliminar(Long id);
    void activarDesactivar(Long id, Boolean activo);
    void asignarCondominio(Long id, Long idCondominio);
    List<AdministradorResult> listarDisponibles();
    List<CondominioSimpleResult> listarCondominiosDisponibles();
}
