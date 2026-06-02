package com.condominios.sgc.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;

import java.util.Map;
import java.util.Objects;

public final class CondominioSpecifications {

    private CondominioSpecifications() {}

    public static Specification<CondominioEntity> porNombre(String nombre) {
        if (nombre == null) return null;
        var escaped = nombre.toLowerCase().replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombre")), "%" + escaped + "%", '\\');
    }

    public static Specification<CondominioEntity> porCiudad(String ciudad) {
        if (ciudad == null) return null;
        return (root, query, cb) -> cb.equal(root.get("ciudad"), ciudad);
    }

    public static Specification<CondominioEntity> porPais(String pais) {
        if (pais == null) return null;
        return (root, query, cb) -> cb.equal(root.get("pais"), pais);
    }

    public static Specification<CondominioEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .filter(e -> e.getValue() != null)
            .map(entry -> switch (entry.getKey()) {
                case "nombre" -> porNombre(entry.getValue());
                case "ciudad" -> porCiudad(entry.getValue());
                case "pais" -> porPais(entry.getValue());
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
