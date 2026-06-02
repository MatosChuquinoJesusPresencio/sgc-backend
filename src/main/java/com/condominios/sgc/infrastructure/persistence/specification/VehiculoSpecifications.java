package com.condominios.sgc.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;

import java.util.Map;
import java.util.Objects;

public final class VehiculoSpecifications {

    private VehiculoSpecifications() {}

    public static Specification<VehiculoEntity> porPlaca(String placa) {
        if (placa == null) return null;
        return (root, query, cb) -> cb.equal(root.get("placa"), placa);
    }

    public static Specification<VehiculoEntity> porPropietarioId(Long usuarioId) {
        if (usuarioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("propietario").get("id"), usuarioId);
    }

    public static Specification<VehiculoEntity> porInquilinoId(Long inquilinoId) {
        if (inquilinoId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("inquilino").get("id"), inquilinoId);
    }

    public static Specification<VehiculoEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .filter(e -> e.getValue() != null)
            .map(entry -> switch (entry.getKey()) {
                case "placa" -> porPlaca(entry.getValue());
                case "propietarioId" -> porPropietarioId(Long.valueOf(entry.getValue()));
                case "inquilinoId" -> porInquilinoId(Long.valueOf(entry.getValue()));
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
