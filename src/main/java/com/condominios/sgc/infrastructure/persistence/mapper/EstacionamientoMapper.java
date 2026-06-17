package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import org.springframework.stereotype.Component;

@Component
public class EstacionamientoMapper {

    public EstacionamientoModel aModelo(EstacionamientoEntity entidad) {
        return new EstacionamientoModel(
            entidad.getId(), entidad.getNumero(), entidad.getTipoVehiculo(),
            entidad.getCapacidadMaxima(), entidad.getCantidadActual(),
            entidad.getDisponible(), entidad.getIdApartamento(),
            entidad.getIdCondominio());
    }

    public EstacionamientoEntity aEntidad(EstacionamientoModel modelo) {
        EstacionamientoEntity entidad = new EstacionamientoEntity();
        entidad.setId(modelo.getId());
        entidad.setNumero(modelo.getNumero());
        entidad.setTipoVehiculo(modelo.getTipoVehiculo());
        entidad.setCapacidadMaxima(modelo.getCapacidadMaxima());
        entidad.setCantidadActual(modelo.getCantidadActual());
        entidad.setDisponible(modelo.getDisponible());
        if (modelo.getIdApartamento() != null) {
            ApartamentoEntity referencia = new ApartamentoEntity();
            referencia.setId(modelo.getIdApartamento());
            entidad.setApartamento(referencia);
        }
        if (modelo.getIdCondominio() != null) {
            CondominioEntity referencia = new CondominioEntity();
            referencia.setId(modelo.getIdCondominio());
            entidad.setCondominio(referencia);
        }
        return entidad;
    }
}
