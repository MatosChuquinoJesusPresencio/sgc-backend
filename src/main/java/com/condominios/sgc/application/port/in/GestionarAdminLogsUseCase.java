package com.condominios.sgc.application.port.in;

import java.time.Instant;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarAdminLogsUseCase {
    PaginaResult<AdminLogEntryResult> listar(String type, Long userId, Instant fechaInicio, Instant fechaFin, PaginaQuery pagina);
}
