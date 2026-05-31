package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;

import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

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

    public static Specification<LogPrestamoCarritoEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .filter(e -> e.getValue() != null)
            .map(entry -> switch (entry.getKey()) {
                case "solicitante" -> porSolicitante(TipoHabitante.valueOf(entry.getValue()));
                case "condominioId" -> porCondominioId(Long.valueOf(entry.getValue()));
                case "apartamentoId" -> porApartamentoId(Long.valueOf(entry.getValue()));
                case "sinDevolucion" -> Boolean.parseBoolean(entry.getValue()) ? porSinDevolucion() : null;
                case "fechaPrestamoDesde" -> porFechaPrestamoEntre(
                    LocalDateTime.parse(entry.getValue()), null);
                case "fechaPrestamoHasta" -> porFechaPrestamoEntre(
                    null, LocalDateTime.parse(entry.getValue()));
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
