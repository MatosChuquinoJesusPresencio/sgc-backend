package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioModel aModelo(UsuarioEntity entidad) {
        return new UsuarioModel(
            entidad.getId(), entidad.getNombres(), entidad.getApellidos(),
            entidad.getCorreo(), entidad.getTelefono(), entidad.getRol(),
            entidad.getActivo(), entidad.getContrasena(),
            entidad.getCorreoPendiente(), entidad.getCorreoVerificado(),
            entidad.getIdCondominio(), entidad.getFechaCreacion());
    }

    public UsuarioEntity aEntidad(UsuarioModel modelo) {
        UsuarioEntity entidad = new UsuarioEntity();
        entidad.setId(modelo.getId());
        entidad.setNombres(modelo.getNombres());
        entidad.setApellidos(modelo.getApellidos());
        entidad.setCorreo(modelo.getCorreo());
        entidad.setTelefono(modelo.getTelefono());
        entidad.setRol(modelo.getRol());
        entidad.setActivo(modelo.getActivo());
        entidad.setContrasena(modelo.getContrasena());
        entidad.setCorreoPendiente(modelo.getCorreoPendiente());
        entidad.setCorreoVerificado(modelo.getCorreoVerificado());
        if (modelo.getIdCondominio() != null) {
            CondominioEntity referencia = new CondominioEntity();
            referencia.setId(modelo.getIdCondominio());
            entidad.setCondominio(referencia);
        }
        entidad.setFechaCreacion(modelo.getFechaCreacion());
        return entidad;
    }
}
