package com.condominios.sgc.infrastructure.persistence.specification;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;

import org.springframework.data.jpa.domain.Specification;
import java.util.Map;
import java.util.Objects;

public final class UsuarioSpecifications {

    private UsuarioSpecifications() {}

    public static Specification<UsuarioEntity> porCorreo(String correo) {
        if (correo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("correo"), correo);
    }

    public static Specification<UsuarioEntity> porRol(Rol rol) {
        if (rol == null) return null;
        return (root, query, cb) -> cb.equal(root.get("rol"), rol);
    }

    public static Specification<UsuarioEntity> porCondominioId(Long condominioId) {
        if (condominioId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("condominio").get("id"), condominioId);
    }

    public static Specification<UsuarioEntity> porActivo(Boolean activo) {
        if (activo == null) return null;
        return (root, query, cb) -> cb.equal(root.get("activo"), activo);
    }

    public static Specification<UsuarioEntity> fromFiltros(Map<String, String> filtros) {
        if (filtros == null || filtros.isEmpty()) return null;
        return filtros.entrySet().stream()
            .map(entry -> switch (entry.getKey()) {
                case "correo" -> porCorreo(entry.getValue());
                case "rol" -> porRol(Rol.valueOf(entry.getValue()));
                case "condominioId" -> porCondominioId(Long.valueOf(entry.getValue()));
                case "activo" -> porActivo(Boolean.valueOf(entry.getValue()));
                default -> null;
            })
            .filter(Objects::nonNull)
            .reduce(Specification::and)
            .orElse(null);
    }
}
