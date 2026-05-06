package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.persistence.entity.EstacionamientoEntity;
import org.springframework.data.jpa.domain.Specification;

public final class EstacionamientoSpecifications {

    private EstacionamientoSpecifications() {}

    public static Specification<EstacionamientoEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("condominio").get("id"), condominioId);
    }

    public static Specification<EstacionamientoEntity> porDisponible(Boolean disponible) {
        if (disponible == null) return null;
        return (root, query, cb) -> cb.equal(root.get("disponible"), disponible);
    }

    public static Specification<EstacionamientoEntity> porTipoVehiculo(TipoVehiculo tipo) {
        if (tipo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("tipoVehiculo"), tipo);
    }

    public static Specification<EstacionamientoEntity> porApartamentoId(Long apartamentoId) {
        if (apartamentoId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("apartamento").get("id"), apartamentoId);
    }
}
