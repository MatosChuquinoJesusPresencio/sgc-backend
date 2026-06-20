package com.condominios.sgc.domain.shared.value_objects;

import com.condominios.sgc.domain.shared.exception.ValueObjectException;

public record Telefono(String numero) {
    public Telefono {
        if (numero == null || numero.isBlank())
            throw ValueObjectException.telefonoRequerido();
    }
}
