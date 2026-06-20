package com.condominios.sgc.domain.shared.value_objects;

import com.condominios.sgc.domain.shared.exception.ValueObjectException;

public record PlacaVehiculo(String valor) {
    public PlacaVehiculo {
        if (valor == null || valor.isBlank())
            throw ValueObjectException.placaRequerida();
    }
}
