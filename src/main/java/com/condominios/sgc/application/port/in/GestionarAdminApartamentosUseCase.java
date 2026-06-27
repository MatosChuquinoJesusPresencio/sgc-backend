package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ActualizarOcupantesCommand;
import com.condominios.sgc.application.dto.command.AsignarPropietarioCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarAdminApartamentosUseCase {
    PaginaResult<AdminApartamentoDetailResult> listar(PaginaQuery pagina);
    void asignarPropietario(Long apartamentoId, AsignarPropietarioCommand cmd);
    void actualizarOcupantes(Long apartamentoId, ActualizarOcupantesCommand cmd);
}
