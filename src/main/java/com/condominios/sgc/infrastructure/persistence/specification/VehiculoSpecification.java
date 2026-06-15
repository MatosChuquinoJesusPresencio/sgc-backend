package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.filter.VehiculoFilter;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class VehiculoSpecification {

    public static Specification<VehiculoEntity> porCondominioYTipo(Long idCondominio, TipoVehiculo tipo) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("idCondominio"), idCondominio),
                cb.equal(root.get("tipo"), tipo));
    }

    public static Specification<VehiculoEntity> porPropietario(Long idPropietario) {
        return (root, query, cb) -> cb.equal(root.get("idPropietario"), idPropietario);
    }

    public static Specification<VehiculoEntity> conFiltro(VehiculoFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.placa() != null)
                predicados.add(cb.like(cb.lower(root.get("placa")), "%" + filtro.placa().toLowerCase() + "%"));
            if (filtro.idPropietario() != null)
                predicados.add(cb.equal(root.get("idPropietario"), filtro.idPropietario()));
            if (filtro.idInquilino() != null)
                predicados.add(cb.equal(root.get("idInquilino"), filtro.idInquilino()));
            if (filtro.marca() != null)
                predicados.add(cb.like(cb.lower(root.get("marca")), "%" + filtro.marca().toLowerCase() + "%"));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
