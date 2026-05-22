package com.condominios.sgc.application.dto;

public record InquilinoResponse(
        Long id,
        String nombres,
        String apellidos,
        String dni,
        Long apartamentoId) {
}