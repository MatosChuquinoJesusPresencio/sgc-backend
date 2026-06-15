package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.PisoFilter;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class PisoSpecification {

    private PisoSpecification() {}

    public static Specification<PisoEntity> porTorre(Long idTorre) {
        return (root, query, cb) -> cb.equal(root.get("idTorre"), idTorre);
    }

    public static Specification<PisoEntity> conFiltro(PisoFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.numero() != null)
                predicados.add(cb.equal(root.get("numero"), filtro.numero()));
            if (filtro.idTorre() != null)
                predicados.add(cb.equal(root.get("idTorre"), filtro.idTorre()));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
