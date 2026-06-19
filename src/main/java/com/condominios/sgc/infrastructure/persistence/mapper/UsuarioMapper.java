package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.shared.value_objects.Correo;
import com.condominios.sgc.domain.shared.value_objects.NombreCompleto;
import com.condominios.sgc.domain.shared.value_objects.Telefono;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;

public class UsuarioMapper {

    public UsuarioModel aDominio(UsuarioEntity entidad) {
        return new UsuarioModel(
            entidad.getId(),
            new NombreCompleto(entidad.getNombres(), entidad.getApellidos()),
            new Correo(entidad.getCorreo()),
            entidad.getTelefono() != null ? new Telefono(entidad.getTelefono()) : null,
            entidad.getRol(),
            entidad.getActivo(),
            entidad.getContrasena(),
            entidad.getCorreoPendiente() != null ? new Correo(entidad.getCorreoPendiente()) : null,
            entidad.getCorreoVerificado(),
            entidad.getIdCondominio(),
            entidad.getFechaCreacion()
        );
    }

    public UsuarioEntity aEntidad(UsuarioModel dominio) {
        UsuarioEntity entidad = new UsuarioEntity();
        entidad.setId(dominio.getId());
        entidad.setNombres(dominio.getNombres());
        entidad.setApellidos(dominio.getApellidos());
        entidad.setCorreo(dominio.getCorreo().direccion());
        entidad.setTelefono(dominio.getTelefono() != null ? dominio.getTelefono().numero() : null);
        entidad.setRol(dominio.getRol());
        entidad.setActivo(dominio.getActivo());
        entidad.setContrasena(dominio.getContrasena());
        entidad.setCorreoPendiente(dominio.getCorreoPendiente() != null ? dominio.getCorreoPendiente().direccion() : null);
        entidad.setCorreoVerificado(dominio.getCorreoVerificado());
        entidad.setIdCondominio(dominio.getIdCondominio());
        entidad.setFechaCreacion(dominio.getFechaCreacion());
        return entidad;
    }
}
