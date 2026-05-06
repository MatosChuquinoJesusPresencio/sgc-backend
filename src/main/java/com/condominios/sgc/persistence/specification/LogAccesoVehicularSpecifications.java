package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.persistence.entity.LogAccesoVehicularEntity;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public final class LogAccesoVehicularSpecifications {

    private LogAccesoVehicularSpecifications() {}

    public static Specification<LogAccesoVehicularEntity> porPlaca(String placa) {
        if (placa == null) return null;
        return (root, query, cb) -> cb.equal(root.get("placa"), placa);
    }

    public static Specification<LogAccesoVehicularEntity> porFechaEntradaEntre(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null && hasta == null) return null;
        return (root, query, cb) -> {
            if (desde != null && hasta != null) {
                return cb.between(root.get("fechaEntrada"), desde, hasta);
            } else if (desde != null) {
                return cb.greaterThanOrEqualTo(root.get("fechaEntrada"), desde);
            } else {
                return cb.lessThanOrEqualTo(root.get("fechaEntrada"), hasta);
            }
        };
    }

    public static Specification<LogAccesoVehicularEntity> porMetodo(MetodoEntrada metodo) {
        if (metodo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("metodo"), metodo);
    }

    public static Specification<LogAccesoVehicularEntity> porSinSalida() {
        return (root, query, cb) -> cb.isNull(root.get("fechaSalida"));
    }

    public static Specification<LogAccesoVehicularEntity> porOcupante(TipoHabitante ocupante) {
        if (ocupante == null) return null;
        return (root, query, cb) -> cb.equal(root.get("ocupante"), ocupante);
    }
}
