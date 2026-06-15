package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.ApartamentoFilter;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class ApartamentoSpecification {

    public static Specification<ApartamentoEntity> conFiltro(ApartamentoFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.numero() != null)
                predicados.add(cb.equal(root.get("numero"), filtro.numero()));
            if (filtro.idPiso() != null)
                predicados.add(cb.equal(root.get("idPiso"), filtro.idPiso()));
            if (filtro.idPropietario() != null)
                predicados.add(cb.equal(root.get("idPropietario"), filtro.idPropietario()));
            if (filtro.derechoEstacionamiento() != null)
                predicados.add(cb.equal(root.get("derechoEstacionamiento"), filtro.derechoEstacionamiento()));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }

    public static Specification<ApartamentoEntity> porCondominio(Long idCondominio) {
        return (root, query, cb) -> cb.equal(root.get("piso").get("torre").get("idCondominio"), idCondominio);
    }
}
