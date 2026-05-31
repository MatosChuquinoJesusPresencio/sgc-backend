package com.condominios.sgc.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;

import java.util.Map;
import java.util.Objects;

public final class ApartamentoSpecifications {

    private ApartamentoSpecifications() {}

    public static Specification<ApartamentoEntity> porPisoId(Long pisoId) {
        if (pisoId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("piso").get("id"), pisoId);
    }

    public static Specification<ApartamentoEntity> porTorreId(Long torreId) {
        if (torreId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("piso").get("torre").get("id"), torreId);
    }

    public static Specification<ApartamentoEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("piso").get("torre").get("condominio").get("id"), condominioId);
    }

    public static Specification<ApartamentoEntity> porPropietarioId(Long usuarioId) {
        if (usuarioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("propietario").get("id"), usuarioId);
    }

    public static Specification<ApartamentoEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .map(entry -> switch (entry.getKey()) {
                case "pisoId" -> porPisoId(Long.valueOf(entry.getValue()));
                case "torreId" -> porTorreId(Long.valueOf(entry.getValue()));
                case "condominioId" -> porCondominioId(Long.valueOf(entry.getValue()));
                case "propietarioId" -> porPropietarioId(Long.valueOf(entry.getValue()));
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
