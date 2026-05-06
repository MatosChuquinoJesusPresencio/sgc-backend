package com.condominios.sgc.persistence.specification;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.domain.Specification;

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
}
