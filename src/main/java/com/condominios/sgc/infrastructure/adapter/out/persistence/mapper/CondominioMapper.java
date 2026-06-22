package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.ConfiguracionEntity;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.TorreEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public final class CondominioMapper {

    private CondominioMapper() {}

    public static CondominioModel toModel(CondominioEntity e) {
        if (e == null) return null;
        List<TorreModel> torres = e.getTorres().stream()
            .map(TorreMapper::toModel)
            .toList();
        ConfiguracionModel config = ConfiguracionMapper.toModel(e.getConfiguracion());
        return new CondominioModel(
            e.getId(),
            e.getNombre(),
            e.getIdPais(),
            e.getIdCiudad(),
            e.getDireccion(),
            e.getActivo(),
            e.getFechaCreacion().toInstant(ZoneOffset.UTC),
            torres,
            config
        );
    }

    public static CondominioModel toModelLigero(CondominioEntity e) {
        if (e == null) return null;
        return new CondominioModel(
            e.getId(),
            e.getNombre(),
            e.getIdPais(),
            e.getIdCiudad(),
            e.getDireccion(),
            e.getActivo(),
            e.getFechaCreacion().toInstant(ZoneOffset.UTC),
            new ArrayList<>(),
            null
        );
    }

    public static CondominioEntity toEntity(CondominioModel m) {
        if (m == null) return null;
        CondominioEntity e = new CondominioEntity();
        e.setId(m.getId());
        e.setNombre(m.getNombre());
        e.setIdPais(m.getIdPais());
        e.setIdCiudad(m.getIdCiudad());
        e.setDireccion(m.getDireccion());
        e.setActivo(m.getActivo());
        e.setFechaCreacion(LocalDateTime.ofInstant(m.getFechaCreacion(), ZoneOffset.UTC));
        if (m.getTorres() != null) {
            List<TorreEntity> torres = m.getTorres().stream()
                .map(TorreMapper::toEntity)
                .toList();
            torres.forEach(t -> t.setCondominio(e));
            e.setTorres(torres);
        }
        if (m.getConfiguracion() != null) {
            ConfiguracionEntity config = ConfiguracionMapper.toEntity(m.getConfiguracion());
            config.setCondominio(e);
            e.setConfiguracion(config);
        }
        return e;
    }
}
