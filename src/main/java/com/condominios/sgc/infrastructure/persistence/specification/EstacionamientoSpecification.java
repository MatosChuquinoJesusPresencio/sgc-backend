package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.EstacionamientoFilter;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class EstacionamientoSpecification {

    public static Specification<EstacionamientoEntity> conFiltro(EstacionamientoFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.numero() != null)
                predicados.add(cb.equal(root.get("numero"), filtro.numero()));
            if (filtro.tipoVehiculo() != null)
                predicados.add(cb.equal(root.get("tipoVehiculo"), filtro.tipoVehiculo()));
            if (filtro.disponible() != null)
                predicados.add(cb.equal(root.get("disponible"), filtro.disponible()));
            if (filtro.idApartamento() != null)
                predicados.add(cb.equal(root.get("idApartamento"), filtro.idApartamento()));
            if (filtro.idCondominio() != null)
                predicados.add(cb.equal(root.get("idCondominio"), filtro.idCondominio()));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }

    public static Specification<EstacionamientoEntity> porCondominio(Long idCondominio) {
        return (root, query, cb) -> cb.equal(root.get("idCondominio"), idCondominio);
    }
}
