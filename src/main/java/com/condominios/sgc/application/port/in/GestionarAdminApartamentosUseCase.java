package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ActualizarOcupantesCommand;
import com.condominios.sgc.application.dto.command.AsignarPropietarioCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarAdminApartamentosUseCase {
    PaginaResult<AdminApartamentoDetailResult> listar(Long condominioIdOverride, PaginaQuery pagina);
    void asignarPropietario(Long condominioIdOverride, Long apartamentoId, AsignarPropietarioCommand cmd);
    void actualizarOcupantes(Long condominioIdOverride, Long apartamentoId, ActualizarOcupantesCommand cmd);
}
