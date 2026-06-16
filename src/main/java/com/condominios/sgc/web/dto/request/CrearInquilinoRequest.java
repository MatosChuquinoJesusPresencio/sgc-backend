package com.condominios.sgc.web.dto.request;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearInquilinoRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotNull TipoDocumento tipoDocumento,
    @NotBlank String numeroDocumento,
    @NotNull Long idApartamento,
    @NotNull Long idCondominio
) {}
