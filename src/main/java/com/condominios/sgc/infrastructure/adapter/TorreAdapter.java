package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.infrastructure.persistence.mapper.TorreMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.TorreRepository;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TorreAdapter implements TorrePort {

    private final TorreRepository torreRepository;
    private final CondominioRepository condominioRepository;

    public TorreAdapter(TorreRepository torreRepository, CondominioRepository condominioRepository) {
        this.torreRepository = torreRepository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    public Optional<TorreModel> findById(Long id) {
        return torreRepository.findById(id).map(TorreMapper::toModel);
    }

    @Override
    public PaginacionResponse<TorreModel> findByCondominioId(Long condominioId, PaginacionRequest request) {
        var page = torreRepository.findByCondominioId(condominioId, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(TorreMapper::toModel).toList());
    }

    @Override
    public TorreModel save(TorreModel model) {
        var entity = TorreMapper.toEntity(model);
        entity.setCondominio(condominioRepository.findById(model.getCondominioId())
            .orElseThrow(() -> new RuntimeException("Condominio no encontrado: " + model.getCondominioId())));
        var saved = torreRepository.save(entity);
        return TorreMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        torreRepository.deleteById(id);
    }
}
