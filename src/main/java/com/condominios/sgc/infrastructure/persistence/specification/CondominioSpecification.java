package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.CondominioFilter;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class CondominioSpecification {

    private CondominioSpecification() {}

    public static Specification<CondominioEntity> conFiltro(CondominioFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.nombre() != null)
                predicados.add(cb.like(cb.lower(root.get("nombre")), "%" + filtro.nombre().toLowerCase() + "%"));
            if (filtro.idPais() != null)
                predicados.add(cb.equal(root.get("idPais"), filtro.idPais()));
            if (filtro.idCiudad() != null)
                predicados.add(cb.equal(root.get("idCiudad"), filtro.idCiudad()));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
