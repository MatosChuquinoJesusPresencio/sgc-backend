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

    public static Specification<VehiculoEntity> porPropietarioUsuarioId(String usuarioId) {
        if (usuarioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("propietarioUsuario").get("id"), usuarioId);
    }

    public static Specification<VehiculoEntity> porPropietarioInquilinoId(Long inquilinoId) {
        if (inquilinoId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("propietarioInquilino").get("id"), inquilinoId);
    }

    public static Specification<VehiculoEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .map(entry -> switch (entry.getKey()) {
                case "placa" -> porPlaca(entry.getValue());
                case "propietarioUsuarioId" -> porPropietarioUsuarioId(entry.getValue());
                case "propietarioInquilinoId" -> porPropietarioInquilinoId(Long.valueOf(entry.getValue()));
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
