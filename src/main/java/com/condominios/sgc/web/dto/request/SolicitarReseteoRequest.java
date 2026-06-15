package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SolicitarReseteoRequest(
    @NotBlank @Email String correo
) {}
