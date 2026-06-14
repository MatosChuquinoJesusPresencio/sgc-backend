package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class LogPrestamoCarritoMapper {

    public LogPrestamoCarritoModel aModelo(LogPrestamoCarritoEntity entidad) {
        return new LogPrestamoCarritoModel(
            entidad.getId(), entidad.getSolicitante(),
            entidad.getNombreSolicitante(), entidad.getDniSolicitante(),
            entidad.getPenalizacion(), entidad.getFechaPrestamo(),
            entidad.getFechaDevolucion(), entidad.getIdApartamento(),
            entidad.getIdCarrito(), entidad.getIdInquilino(),
            entidad.getIdPropietario());
    }

    public LogPrestamoCarritoEntity aEntidad(LogPrestamoCarritoModel modelo) {
        LogPrestamoCarritoEntity entidad = new LogPrestamoCarritoEntity();
        entidad.setId(modelo.getId());
        entidad.setSolicitante(modelo.getSolicitante());
        entidad.setNombreSolicitante(modelo.getNombreSolicitante());
        entidad.setDniSolicitante(modelo.getDniSolicitante());
        entidad.setPenalizacion(modelo.getPenalizacion());
        entidad.setFechaPrestamo(modelo.getFechaPrestamo());
        entidad.setFechaDevolucion(modelo.getFechaDevolucion());
        if (modelo.getIdApartamento() != null) {
            ApartamentoEntity referencia = new ApartamentoEntity();
            referencia.setId(modelo.getIdApartamento());
            entidad.setApartamento(referencia);
        }
        if (modelo.getIdCarrito() != null) {
            CarritoEntity referencia = new CarritoEntity();
            referencia.setId(modelo.getIdCarrito());
            entidad.setCarrito(referencia);
        }
        if (modelo.getIdInquilino() != null) {
            InquilinoEntity referencia = new InquilinoEntity();
            referencia.setId(modelo.getIdInquilino());
            entidad.setInquilino(referencia);
        }
        if (modelo.getIdPropietario() != null) {
            UsuarioEntity referencia = new UsuarioEntity();
            referencia.setId(modelo.getIdPropietario());
            entidad.setPropietario(referencia);
        }
        return entidad;
    }
}
