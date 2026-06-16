package com.condominios.sgc.web.dto.request;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarInquilinoRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotNull TipoDocumento tipoDocumento,
    @NotBlank String numeroDocumento,
    Long idApartamento,
    boolean desasignarApartamento
) {}
