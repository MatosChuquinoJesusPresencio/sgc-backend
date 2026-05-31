package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.infrastructure.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CondominioAdapter implements CondominioPort {

    private final CondominioRepository condominioRepository;

    public CondominioAdapter(CondominioRepository condominioRepository) {
        this.condominioRepository = condominioRepository;
    }

    @Override
    public Optional<CondominioModel> findById(Long id) {
        return condominioRepository.findById(id).map(CondominioMapper::toModel);
    }

    @Override
    public PaginacionResponse<CondominioModel> findAll(PaginacionRequest request) {
        var page = condominioRepository.findAll(PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(CondominioMapper::toModel).toList());
    }

    @Override
    public CondominioModel save(CondominioModel model) {
        var entity = CondominioMapper.toEntity(model);
        var saved = condominioRepository.save(entity);
        return CondominioMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        condominioRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return condominioRepository.existsByNombre(nombre);
    }
}
