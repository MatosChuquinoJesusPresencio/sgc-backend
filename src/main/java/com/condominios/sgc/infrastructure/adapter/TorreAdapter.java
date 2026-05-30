package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.TorreMapper;
import com.condominios.sgc.infrastructure.persistence.repository.TorreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TorreAdapter implements TorrePort {
    private final TorreRepository torreRepository;

    public TorreAdapter(TorreRepository torreRepository) {
        this.torreRepository = torreRepository;
    }

    @Override
    public Optional<TorreModel> findById(Long id) {
        return torreRepository.findById(id).map(TorreMapper::toModel);
    }

    @Override
    public PaginacionResponse<TorreModel> findByCondominioId(Long condominioId, PaginacionRequest request) {
        Page<TorreEntity> page = torreRepository.findByCondominioId(
                condominioId, PageRequest.of(request.pagina(), request.tamanio())
        );

        List<TorreModel> modelos = page.getContent().stream().map(TorreMapper::toModel).toList();
        return new PaginacionResponse<>(modelos, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public TorreModel save(TorreModel torre) {
        TorreEntity entity = TorreMapper.toEntity(torre);

        if (torre.getCondominioId() != null) {
            CondominioEntity condominioEntity = new CondominioEntity();
            condominioEntity.setId(torre.getCondominioId());
            entity.setCondominio(condominioEntity);
        }

        return TorreMapper.toModel(torreRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        torreRepository.deleteById(id);
    }
}