package com.condominios.sgc.application.dto.query;

public record ListarApartamentosQuery(
    int pagina,
    int tamano,
    Integer numero,
    Long idPiso,
    Long idPropietario,
    Boolean derechoEstacionamiento
) {}
