package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.TorreFilter;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class TorreSpecification {

    public static Specification<TorreEntity> conFiltro(TorreFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.nombre() != null)
                predicados.add(cb.like(cb.lower(root.get("nombre")), "%" + filtro.nombre().toLowerCase() + "%"));
            if (filtro.idCondominio() != null)
                predicados.add(cb.equal(root.get("idCondominio"), filtro.idCondominio()));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
