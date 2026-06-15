package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.InquilinoFilter;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class InquilinoSpecification {

    private InquilinoSpecification() {}

    public static Specification<InquilinoEntity> porApartamento(Long idApartamento) {
        return (root, query, cb) -> cb.equal(root.get("idApartamento"), idApartamento);
    }

    public static Specification<InquilinoEntity> conFiltro(InquilinoFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.nombres() != null)
                predicados.add(cb.like(cb.lower(root.get("nombres")), "%" + filtro.nombres().toLowerCase() + "%"));
            if (filtro.apellidos() != null)
                predicados.add(cb.like(cb.lower(root.get("apellidos")), "%" + filtro.apellidos().toLowerCase() + "%"));
            if (filtro.tipoDocumento() != null)
                predicados.add(cb.equal(root.get("tipoDocumento"), filtro.tipoDocumento()));
            if (filtro.numeroDocumento() != null)
                predicados.add(cb.like(cb.lower(root.get("numeroDocumento")), "%" + filtro.numeroDocumento().toLowerCase() + "%"));
            if (filtro.idApartamento() != null)
                predicados.add(cb.equal(root.get("idApartamento"), filtro.idApartamento()));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
