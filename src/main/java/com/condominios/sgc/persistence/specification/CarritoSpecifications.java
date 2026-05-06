package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.persistence.entity.CarritoEntity;
import org.springframework.data.jpa.domain.Specification;

public final class CarritoSpecifications {

    private CarritoSpecifications() {}

    public static Specification<CarritoEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("condominio").get("id"), condominioId);
    }

    public static Specification<CarritoEntity> porEstado(EstadoCarrito estado) {
        if (estado == null) return null;
        return (root, query, cb) -> cb.equal(root.get("estado"), estado);
    }

    public static Specification<CarritoEntity> porCodigo(String codigo) {
        if (codigo == null) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("codigo")), "%" + codigo.toLowerCase() + "%");
    }
}
