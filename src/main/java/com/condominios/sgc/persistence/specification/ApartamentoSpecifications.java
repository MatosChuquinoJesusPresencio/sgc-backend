package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.persistence.entity.ApartamentoEntity;
import org.springframework.data.jpa.domain.Specification;

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

    public static Specification<ApartamentoEntity> porPropietarioId(String usuarioId) {
        if (usuarioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("propietario").get("id"), usuarioId);
    }
}
