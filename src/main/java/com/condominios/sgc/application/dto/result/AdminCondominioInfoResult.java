package com.condominios.sgc.application.dto.result;

import java.math.BigDecimal;

public record AdminCondominioInfoResult(
    Long id,
    String nombre,
    Long idPais,
    String nombrePais,
    Long idCiudad,
    String nombreCiudad,
    String direccion,
    Boolean activo,
    Integer maxAutos,
    Integer maxMotos,
    BigDecimal penalizacionPorMin,
    Integer maxTiempoPrestamoMin,
    Integer maxEstacionamientos,
    Integer maxCarritos,
    Integer maxVehiculos,
    Integer maxInquilinos
) {
}
