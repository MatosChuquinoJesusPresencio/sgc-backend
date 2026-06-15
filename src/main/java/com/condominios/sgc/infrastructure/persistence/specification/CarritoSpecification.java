package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.CarritoFilter;
import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class CarritoSpecification {

    private CarritoSpecification() {}

    public static Specification<CarritoEntity> porCondominio(Long idCondominio) {
        return (root, query, cb) -> cb.equal(root.get("idCondominio"), idCondominio);
    }

    public static Specification<CarritoEntity> conFiltro(CarritoFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.codigo() != null)
                predicados.add(cb.like(cb.lower(root.get("codigo")), "%" + filtro.codigo().toLowerCase() + "%"));
            if (filtro.estado() != null)
                predicados.add(cb.equal(root.get("estado"), filtro.estado()));
            if (filtro.idCondominio() != null)
                predicados.add(cb.equal(root.get("idCondominio"), filtro.idCondominio()));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
