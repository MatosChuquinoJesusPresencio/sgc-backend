package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.persistence.entity.CondominioEntity;
import org.springframework.data.jpa.domain.Specification;

public final class CondominioSpecifications {

    private CondominioSpecifications() {}

    public static Specification<CondominioEntity> porNombre(String nombre) {
        if (nombre == null) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
    }

    public static Specification<CondominioEntity> porCiudad(String ciudad) {
        if (ciudad == null) return null;
        return (root, query, cb) -> cb.equal(root.get("ciudad"), ciudad);
    }

    public static Specification<CondominioEntity> porPais(String pais) {
        if (pais == null) return null;
        return (root, query, cb) -> cb.equal(root.get("pais"), pais);
    }
}
