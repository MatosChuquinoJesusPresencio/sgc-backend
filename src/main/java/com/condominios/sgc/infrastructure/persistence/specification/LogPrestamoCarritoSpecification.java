package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.LogPrestamoCarritoFilter;
import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class LogPrestamoCarritoSpecification {

    public static Specification<LogPrestamoCarritoEntity> conFiltro(LogPrestamoCarritoFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.nombreSolicitante() != null)
                predicados.add(cb.like(cb.lower(root.get("nombreSolicitante")), "%" + filtro.nombreSolicitante().toLowerCase() + "%"));
            if (filtro.dniSolicitante() != null)
                predicados.add(cb.like(cb.lower(root.get("dniSolicitante")), "%" + filtro.dniSolicitante().toLowerCase() + "%"));
            if (filtro.solicitante() != null)
                predicados.add(cb.equal(root.get("solicitante"), filtro.solicitante()));
            if (filtro.idApartamento() != null)
                predicados.add(cb.equal(root.get("idApartamento"), filtro.idApartamento()));
            if (filtro.idCarrito() != null)
                predicados.add(cb.equal(root.get("idCarrito"), filtro.idCarrito()));
            if (filtro.fechaDesde() != null)
                predicados.add(cb.greaterThanOrEqualTo(root.get("fechaPrestamo"), filtro.fechaDesde()));
            if (filtro.fechaHasta() != null)
                predicados.add(cb.lessThanOrEqualTo(root.get("fechaPrestamo"), filtro.fechaHasta()));
            if (filtro.sinDevolucion() != null && filtro.sinDevolucion())
                predicados.add(cb.isNull(root.get("fechaDevolucion")));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
