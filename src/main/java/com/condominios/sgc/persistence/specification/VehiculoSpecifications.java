package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.persistence.entity.VehiculoEntity;
import org.springframework.data.jpa.domain.Specification;

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
}
