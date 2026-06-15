package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.LogAccesoVehicularFilter;
import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class LogAccesoVehicularSpecification {

    private LogAccesoVehicularSpecification() {}

    public static Specification<LogAccesoVehicularEntity> conFiltro(LogAccesoVehicularFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.placa() != null)
                predicados.add(cb.like(cb.lower(root.get("placa")), "%" + filtro.placa().toLowerCase() + "%"));
            if (filtro.ocupante() != null)
                predicados.add(cb.equal(root.get("ocupante"), filtro.ocupante()));
            if (filtro.metodo() != null)
                predicados.add(cb.equal(root.get("metodo"), filtro.metodo()));
            if (filtro.idVehiculo() != null)
                predicados.add(cb.equal(root.get("idVehiculo"), filtro.idVehiculo()));
            if (filtro.idEstacionamiento() != null)
                predicados.add(cb.equal(root.get("idEstacionamiento"), filtro.idEstacionamiento()));
            if (filtro.fechaDesde() != null)
                predicados.add(cb.greaterThanOrEqualTo(root.get("fechaEntrada"), filtro.fechaDesde()));
            if (filtro.fechaHasta() != null)
                predicados.add(cb.lessThanOrEqualTo(root.get("fechaEntrada"), filtro.fechaHasta()));
            if (filtro.sinSalida() != null && filtro.sinSalida())
                predicados.add(cb.isNull(root.get("fechaSalida")));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
