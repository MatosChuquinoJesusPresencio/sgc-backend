package com.condominios.sgc.application.port.in;

import java.time.Instant;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarPropietarioLogsUseCase {
    PaginaResult<AdminLogEntryResult> listar(Long condominioIdOverride, String type, Instant fechaInicio, Instant fechaFin, PaginaQuery pagina);
}
