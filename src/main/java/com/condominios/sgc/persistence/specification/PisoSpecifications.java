package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.persistence.entity.PisoEntity;
import org.springframework.data.jpa.domain.Specification;

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
}
