package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;

import org.springframework.data.jpa.domain.Specification;
import java.util.Map;
import java.util.Objects;

public final class CarritoSpecifications {

    private CarritoSpecifications() {}

    public static Specification<CarritoEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("condominio").get("id"), condominioId);
    }

    public static Specification<CarritoEntity> porEstado(EstadoCarrito estado) {
        if (estado == null) return null;
        return (root, query, cb) -> cb.equal(root.get("estado"), estado);
    }

    public static Specification<CarritoEntity> porCodigo(String codigo) {
        if (codigo == null) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("codigo")), "%" + codigo.toLowerCase() + "%");
    }

    public static Specification<CarritoEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .map(entry -> switch (entry.getKey()) {
                case "condominioId" -> porCondominioId(Long.valueOf(entry.getValue()));
                case "estado" -> porEstado(EstadoCarrito.valueOf(entry.getValue()));
                case "codigo" -> porCodigo(entry.getValue());
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
