package com.condominios.sgc.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record CondominioTreeResponse(
        Long id,
        String nombre,
        String pais,
        String ciudad,
        String direccion,
        List<TorreNode> torres
) {
    public record TorreNode(
            Long id,
            String nombre,
            List<PisoNode> pisos
    ) {}

    public record PisoNode(
            Long id,
            Integer numero,
            List<ApartamentoNode> apartamentos
    ) {}

    public record ApartamentoNode(
            Long id,
            Integer numero,
            Boolean derechoEstacionamiento,
            BigDecimal metraje,
            String propietarioId,
            List<InquilinoNode> inquilinos
    ) {}

    public record InquilinoNode(
            Long id,
            String nombres,
            String apellidos,
            String dni
    ) {}
}