package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.shared.valueobject.Correo;
import com.condominios.sgc.domain.shared.valueobject.NombreCompleto;
import com.condominios.sgc.domain.shared.valueobject.Telefono;
import com.condominios.sgc.domain.type.Rol;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioModel toModel(UsuarioEntity e) {
        if (e == null) return null;
        return new UsuarioModel(
            e.getId(),
            new NombreCompleto(e.getNombres(), e.getApellidos()),
            new Correo(e.getCorreo()),
            e.getTelefono() != null ? new Telefono(e.getTelefono()) : null,
            Rol.valueOf(e.getRol()),
            e.getActivo(),
            e.getContrasena(),
            e.getCorreoPendiente() != null ? new Correo(e.getCorreoPendiente()) : null,
            e.getCorreoVerificado(),
            e.getIdCondominio(),
            e.getFechaCreacion().toInstant(ZoneOffset.UTC)
        );
    }

    public static UsuarioEntity toEntity(UsuarioModel m) {
        if (m == null) return null;
        UsuarioEntity e = new UsuarioEntity();
        e.setId(m.getId());
        e.setNombres(m.getNombres());
        e.setApellidos(m.getApellidos());
        e.setCorreo(m.getCorreo().direccion());
        e.setTelefono(m.getTelefono() != null ? m.getTelefono().numero() : null);
        e.setRol(m.getRol().name());
        e.setActivo(m.getActivo());
        e.setContrasena(m.getContrasena());
        e.setCorreoPendiente(m.getCorreoPendiente() != null ? m.getCorreoPendiente().direccion() : null);
        e.setCorreoVerificado(m.getCorreoVerificado());
        e.setIdCondominio(m.getIdCondominio());
        e.setFechaCreacion(LocalDateTime.ofInstant(m.getFechaCreacion(), ZoneOffset.UTC));
        return e;
    }
}
