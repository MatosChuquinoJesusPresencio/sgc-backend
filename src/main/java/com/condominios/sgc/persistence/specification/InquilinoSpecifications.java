package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.persistence.entity.InquilinoEntity;
import org.springframework.data.jpa.domain.Specification;

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
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombres")), "%" + nombres.toLowerCase() + "%");
    }
}
