package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;

import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

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

    public static Specification<LogAccesoVehicularEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> {
            var vehiculo = root.join("vehiculo");
            var propietario = vehiculo.join("propietario");
            return cb.equal(propietario.get("condominio").get("id"), condominioId);
        };
    }

    public static Specification<LogAccesoVehicularEntity> porApartamentoId(Long apartamentoId) {
        if (apartamentoId == null) return null;
        return (root, query, cb) -> {
            var vehiculo = root.join("vehiculo");
            var inquilino = vehiculo.join("inquilino");
            return cb.equal(inquilino.get("apartamento").get("id"), apartamentoId);
        };
    }

    public static Specification<LogAccesoVehicularEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .filter(e -> e.getValue() != null)
            .map(entry -> switch (entry.getKey()) {
                case "placa" -> porPlaca(entry.getValue());
                case "metodo" -> porMetodo(MetodoEntrada.valueOf(entry.getValue()));
                case "ocupante" -> porOcupante(TipoHabitante.valueOf(entry.getValue()));
                case "sinSalida" -> Boolean.parseBoolean(entry.getValue()) ? porSinSalida() : null;
                case "fechaEntradaDesde" -> porFechaEntradaEntre(
                    LocalDateTime.parse(entry.getValue()), null);
                case "fechaEntradaHasta" -> porFechaEntradaEntre(
                    null, LocalDateTime.parse(entry.getValue()));
                case "condominioId" -> porCondominioId(Long.valueOf(entry.getValue()));
                case "apartamentoId" -> porApartamentoId(Long.valueOf(entry.getValue()));
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
