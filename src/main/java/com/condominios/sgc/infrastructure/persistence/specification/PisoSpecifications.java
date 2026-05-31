package com.condominios.sgc.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;

import java.util.Map;
import java.util.Objects;

public final class PisoSpecifications {

    private PisoSpecifications() {}

    public static Specification<PisoEntity> porTorreId(Long torreId) {
        if (torreId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("torre").get("id"), torreId);
    }

    public static Specification<PisoEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("torre").get("condominio").get("id"), condominioId);
    }

    public static Specification<PisoEntity> porNumero(Integer numero) {
        if (numero == null) return null;
        return (root, query, cb) -> cb.equal(root.get("numero"), numero);
    }

    public static Specification<PisoEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .filter(e -> e.getValue() != null)
            .map(entry -> switch (entry.getKey()) {
                case "torreId" -> porTorreId(Long.valueOf(entry.getValue()));
                case "condominioId" -> porCondominioId(Long.valueOf(entry.getValue()));
                case "numero" -> porNumero(Integer.valueOf(entry.getValue()));
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
