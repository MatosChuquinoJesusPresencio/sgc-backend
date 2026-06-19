package com.condominios.sgc.domain.shared.value_objects;

import com.condominios.sgc.domain.shared.exception.ValueObjectException;

public record Correo(String direccion) {
    private static final String PATRON = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

    public Correo {
        if (direccion == null || direccion.isBlank())
            throw ValueObjectException.correoRequerido();
        if (!direccion.matches(PATRON))
            throw ValueObjectException.correoInvalido();
    }
}
