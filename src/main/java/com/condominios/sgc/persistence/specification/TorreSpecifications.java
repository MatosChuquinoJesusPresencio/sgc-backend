package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.persistence.entity.TorreEntity;
import org.springframework.data.jpa.domain.Specification;

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
}
