package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    public VehiculoModel aModelo(VehiculoEntity entidad) {
        return new VehiculoModel(
            entidad.getId(), entidad.getMarca(), entidad.getColor(),
            entidad.getModelo(), entidad.getPlaca(), entidad.getTipo(),
            entidad.getIdPropietario(), entidad.getIdInquilino(),
            entidad.getIdEstacionamiento(), entidad.getIdCondominio());
    }

    public VehiculoEntity aEntidad(VehiculoModel modelo) {
        VehiculoEntity entidad = new VehiculoEntity();
        entidad.setId(modelo.getId());
        entidad.setMarca(modelo.getMarca());
        entidad.setColor(modelo.getColor());
        entidad.setModelo(modelo.getModelo());
        entidad.setPlaca(modelo.getPlaca());
        entidad.setTipo(modelo.getTipo());
        if (modelo.getIdPropietario() != null) {
            UsuarioEntity referencia = new UsuarioEntity();
            referencia.setId(modelo.getIdPropietario());
            entidad.setPropietario(referencia);
        }
        if (modelo.getIdInquilino() != null) {
            InquilinoEntity referencia = new InquilinoEntity();
            referencia.setId(modelo.getIdInquilino());
            entidad.setInquilino(referencia);
        }
        if (modelo.getIdEstacionamiento() != null) {
            EstacionamientoEntity referencia = new EstacionamientoEntity();
            referencia.setId(modelo.getIdEstacionamiento());
            entidad.setEstacionamiento(referencia);
        }
        if (modelo.getIdCondominio() != null) {
            CondominioEntity referencia = new CondominioEntity();
            referencia.setId(modelo.getIdCondominio());
            entidad.setCondominio(referencia);
        }
        return entidad;
    }
}
