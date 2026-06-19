package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CondominioMapper {

    private final TorreMapper torreMapper = new TorreMapper();
    private final PisoMapper pisoMapper = new PisoMapper();
    private final ApartamentoMapper apartamentoMapper = new ApartamentoMapper();
    private final ConfiguracionMapper configuracionMapper = new ConfiguracionMapper();

    public CondominioModel aDominio(CondominioEntity entidad) {
        List<TorreModel> torres = entidad.getTorres().stream()
            .map(this::aTorreDominio)
            .collect(Collectors.toList());

        ConfiguracionModel config = entidad.getConfiguracion() != null
            ? configuracionMapper.aDominio(entidad.getConfiguracion())
            : null;

        return new CondominioModel(
            entidad.getId(),
            entidad.getNombre(),
            entidad.getIdPais(),
            entidad.getIdCiudad(),
            entidad.getDireccion(),
            entidad.getActivo(),
            entidad.getFechaCreacion(),
            torres,
            config
        );
    }

    private TorreModel aTorreDominio(TorreEntity entidad) {
        List<PisoModel> pisos = entidad.getPisos().stream()
            .map(this::aPisoDominio)
            .collect(Collectors.toList());

        return new TorreModel(entidad.getId(), entidad.getNombre(), pisos);
    }

    private PisoModel aPisoDominio(PisoEntity entidad) {
        List<ApartamentoModel> apartamentos = entidad.getApartamentos().stream()
            .map(apartamentoMapper::aDominio)
            .collect(Collectors.toList());

        return new PisoModel(entidad.getId(), entidad.getNumero(), apartamentos);
    }

    public CondominioEntity aEntidad(CondominioModel dominio) {
        CondominioEntity entidad = new CondominioEntity();
        entidad.setId(dominio.getId());
        entidad.setNombre(dominio.getNombre());
        entidad.setIdPais(dominio.getIdPais());
        entidad.setIdCiudad(dominio.getIdCiudad());
        entidad.setDireccion(dominio.getDireccion());
        entidad.setActivo(dominio.getActivo());
        entidad.setFechaCreacion(dominio.getFechaCreacion());

        List<TorreEntity> torres = dominio.getTorres().stream()
            .map(t -> aTorreEntidad(t, entidad))
            .collect(Collectors.toList());
        entidad.setTorres(torres);

        if (dominio.getConfiguracion() != null) {
            entidad.setConfiguracion(aConfigEntidad(dominio.getConfiguracion(), entidad));
        }

        return entidad;
    }

    private TorreEntity aTorreEntidad(TorreModel dominio, CondominioEntity padre) {
        TorreEntity entidad = torreMapper.aEntidad(dominio);
        entidad.setCondominio(padre);

        List<PisoEntity> pisos = dominio.getPisos().stream()
            .map(p -> aPisoEntidad(p, entidad))
            .collect(Collectors.toList());
        entidad.setPisos(pisos);

        return entidad;
    }

    private PisoEntity aPisoEntidad(PisoModel dominio, TorreEntity padre) {
        PisoEntity entidad = pisoMapper.aEntidad(dominio);
        entidad.setTorre(padre);

        List<ApartamentoEntity> apartamentos = dominio.getApartamentos().stream()
            .map(a -> aApartamentoEntidad(a, entidad))
            .collect(Collectors.toList());
        entidad.setApartamentos(apartamentos);

        return entidad;
    }

    private ApartamentoEntity aApartamentoEntidad(ApartamentoModel dominio, PisoEntity padre) {
        ApartamentoEntity entidad = apartamentoMapper.aEntidad(dominio);
        entidad.setPiso(padre);
        return entidad;
    }

    private ConfiguracionEntity aConfigEntidad(ConfiguracionModel dominio, CondominioEntity padre) {
        ConfiguracionEntity entidad = configuracionMapper.aEntidad(dominio);
        entidad.setCondominio(padre);
        return entidad;
    }
}
