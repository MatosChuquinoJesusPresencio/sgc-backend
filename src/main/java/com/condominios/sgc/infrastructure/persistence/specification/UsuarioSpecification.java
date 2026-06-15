package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.filter.UsuarioFilter;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class UsuarioSpecification {

    public static Specification<UsuarioEntity> porCondominio(Long idCondominio) {
        return (root, query, cb) -> cb.equal(root.get("idCondominio"), idCondominio);
    }

    public static Specification<UsuarioEntity> conFiltro(UsuarioFilter filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (filtro.nombres() != null)
                predicados.add(cb.like(cb.lower(root.get("nombres")), "%" + filtro.nombres().toLowerCase() + "%"));
            if (filtro.apellidos() != null)
                predicados.add(cb.like(cb.lower(root.get("apellidos")), "%" + filtro.apellidos().toLowerCase() + "%"));
            if (filtro.correo() != null)
                predicados.add(cb.like(cb.lower(root.get("correo")), "%" + filtro.correo().toLowerCase() + "%"));
            if (filtro.rol() != null)
                predicados.add(cb.equal(root.get("rol"), filtro.rol()));
            if (filtro.activo() != null)
                predicados.add(cb.equal(root.get("activo"), filtro.activo()));
            if (filtro.idCondominio() != null)
                predicados.add(cb.equal(root.get("idCondominio"), filtro.idCondominio()));
            return cb.and(predicados.toArray(Predicate[]::new));
        };
    }
}
