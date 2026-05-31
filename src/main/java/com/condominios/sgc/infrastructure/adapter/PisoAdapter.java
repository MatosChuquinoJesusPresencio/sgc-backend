package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.infrastructure.persistence.mapper.PisoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.PisoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.TorreRepository;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PisoAdapter implements PisoPort {

    private final PisoRepository pisoRepository;
    private final TorreRepository torreRepository;

    public PisoAdapter(PisoRepository pisoRepository, TorreRepository torreRepository) {
        this.pisoRepository = pisoRepository;
        this.torreRepository = torreRepository;
    }

    @Override
    public Optional<PisoModel> findById(Long id) {
        return pisoRepository.findById(id).map(PisoMapper::toModel);
    }

    @Override
    public PaginacionResponse<PisoModel> findByTorreId(Long torreId, PaginacionRequest request) {
        var page = pisoRepository.findByTorreId(torreId, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(PisoMapper::toModel).toList());
    }

    @Override
    public PisoModel save(PisoModel model) {
        var entity = PisoMapper.toEntity(model);
        entity.setTorre(torreRepository.findById(model.getTorreId())
            .orElseThrow(() -> TorreException.noEncontrada(model.getTorreId())));
        var saved = pisoRepository.save(entity);
        return PisoMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        pisoRepository.deleteById(id);
    }
}
