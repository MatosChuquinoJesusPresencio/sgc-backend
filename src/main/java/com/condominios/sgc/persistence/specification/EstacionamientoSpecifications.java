package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.persistence.entity.EstacionamientoEntity;
import org.springframework.data.jpa.domain.Specification;
import java.util.Map;
import java.util.Objects;

public final class EstacionamientoSpecifications {

    private EstacionamientoSpecifications() {}

    public static Specification<EstacionamientoEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("condominio").get("id"), condominioId);
    }

    public static Specification<EstacionamientoEntity> porDisponible(Boolean disponible) {
        if (disponible == null) return null;
        return (root, query, cb) -> cb.equal(root.get("disponible"), disponible);
    }

    public static Specification<EstacionamientoEntity> porTipoVehiculo(TipoVehiculo tipo) {
        if (tipo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("tipoVehiculo"), tipo);
    }

    public static Specification<EstacionamientoEntity> porApartamentoId(Long apartamentoId) {
        if (apartamentoId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("apartamento").get("id"), apartamentoId);
    }

    public static Specification<EstacionamientoEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .map(entry -> switch (entry.getKey()) {
                case "condominioId" -> porCondominioId(Long.valueOf(entry.getValue()));
                case "disponible" -> porDisponible(Boolean.valueOf(entry.getValue()));
                case "tipoVehiculo" -> porTipoVehiculo(TipoVehiculo.valueOf(entry.getValue()));
                case "apartamentoId" -> porApartamentoId(Long.valueOf(entry.getValue()));
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
