package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.command.ActualizarOcupantesCommand;
import com.condominios.sgc.application.dto.command.AsignarPropietarioCommand;
import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;

public interface GestionarAdminApartamentosUseCase {
    List<AdminApartamentoDetailResult> listar();
    void asignarPropietario(Long apartamentoId, AsignarPropietarioCommand cmd);
    void actualizarOcupantes(Long apartamentoId, ActualizarOcupantesCommand cmd);
}
