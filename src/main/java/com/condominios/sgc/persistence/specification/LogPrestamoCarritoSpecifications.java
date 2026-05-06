package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.persistence.entity.LogPrestamoCarritoEntity;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public final class LogPrestamoCarritoSpecifications {

    private LogPrestamoCarritoSpecifications() {}

    public static Specification<LogPrestamoCarritoEntity> porSolicitante(TipoHabitante solicitante) {
        if (solicitante == null) return null;
        return (root, query, cb) -> cb.equal(root.get("solicitante"), solicitante);
    }

    public static Specification<LogPrestamoCarritoEntity> porFechaPrestamoEntre(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null && hasta == null) return null;
        return (root, query, cb) -> {
            if (desde != null && hasta != null) {
                return cb.between(root.get("fechaPrestamo"), desde, hasta);
            } else if (desde != null) {
                return cb.greaterThanOrEqualTo(root.get("fechaPrestamo"), desde);
            } else {
                return cb.lessThanOrEqualTo(root.get("fechaPrestamo"), hasta);
            }
        };
    }

    public static Specification<LogPrestamoCarritoEntity> porSinDevolucion() {
        return (root, query, cb) -> cb.isNull(root.get("fechaDevolucion"));
    }

    public static Specification<LogPrestamoCarritoEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("carrito").get("condominio").get("id"), condominioId);
    }

    public static Specification<LogPrestamoCarritoEntity> porApartamentoId(Long apartamentoId) {
        if (apartamentoId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("apartamento").get("id"), apartamentoId);
    }
}
