package com.condominios.sgc.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;

import java.util.Map;
import java.util.Objects;

public final class TorreSpecifications {

    private TorreSpecifications() {}

    public static Specification<TorreEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("condominio").get("id"), condominioId);
    }

    public static Specification<TorreEntity> porNombre(String nombre) {
        if (nombre == null) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
    }

    public static Specification<TorreEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .map(entry -> switch (entry.getKey()) {
                case "condominioId" -> porCondominioId(Long.valueOf(entry.getValue()));
                case "nombre" -> porNombre(entry.getValue());
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
