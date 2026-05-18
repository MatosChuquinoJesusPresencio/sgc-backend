package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.TorreMapper;
import com.condominios.sgc.infrastructure.persistence.repository.TorreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class TorreAdapter {
    private final TorreRepository torreRepository;
    private final TorreMapper torreMapper;

    public TorreAdapter(TorreRepository torreRepository, TorreMapper torreMapper) {
        this.torreRepository = torreRepository;
        this.torreMapper = torreMapper;
    }

    @Override
    public TorreModel findById(Long id) {
        return torreRepository.findById(id).map(torreMapper::toModel).orElse(null);
    }

    @Override
    public PaginacionResponse<TorreModel> findByCondominioId(Long condominioId, PaginacionRequest request) {
        Page<TorreEntity> page = torreRepository.findByCondominioId(
                condominioId, PageRequest.of(request.getPage(), request.getSize())
        );

        List<TorreModel> modelos = page.getContent().stream().map(torreMapper::toModel).toList();
        return new PaginacionResponse<>(modelos, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    public TorreModel save(TorreModel torre) {
        TorreEntity entity = torreMapper.toEntity(torre);
        return torreMapper.toModel(torreRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        torreRepository.deleteById(id);
    }
}
