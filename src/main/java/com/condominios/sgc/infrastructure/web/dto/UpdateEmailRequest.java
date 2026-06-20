package com.condominios.sgc.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailRequest(@NotBlank @Email String email) {
}
