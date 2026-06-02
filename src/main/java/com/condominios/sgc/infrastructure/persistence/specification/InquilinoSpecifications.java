package com.condominios.sgc.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;

import java.util.Map;
import java.util.Objects;

public final class InquilinoSpecifications {

    private InquilinoSpecifications() {}

    public static Specification<InquilinoEntity> porApartamentoId(Long apartamentoId) {
        if (apartamentoId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("apartamento").get("id"), apartamentoId);
    }

    public static Specification<InquilinoEntity> porDni(String dni) {
        if (dni == null) return null;
        return (root, query, cb) -> cb.equal(root.get("dni"), dni);
    }

    public static Specification<InquilinoEntity> porNombre(String nombres) {
        if (nombres == null) return null;
        var escaped = nombres.toLowerCase().replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombres")), "%" + escaped + "%", '\\');
    }

    public static Specification<InquilinoEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .filter(e -> e.getValue() != null)
            .map(entry -> switch (entry.getKey()) {
                case "apartamentoId" -> porApartamentoId(Long.valueOf(entry.getValue()));
                case "dni" -> porDni(entry.getValue());
                case "nombre" -> porNombre(entry.getValue());
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
