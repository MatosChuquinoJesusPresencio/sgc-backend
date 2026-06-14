package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.CiudadModel;
import com.condominios.sgc.domain.port.CiudadPort;
import com.condominios.sgc.infrastructure.persistence.mapper.CiudadMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CiudadRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class CiudadAdapter implements CiudadPort {

    private final CiudadRepository repository;
    private final CiudadMapper mapper;

    public CiudadAdapter(CiudadRepository repository, CiudadMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CiudadModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public List<CiudadModel> obtenerPorPais(Long idPais) {
        return repository.findByIdPais(idPais).stream().map(mapper::aModelo).toList();
    }
}
