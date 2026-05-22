package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.PisoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.PisoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PisoAdapter implements PisoPort {
    private final PisoRepository pisoRepository;

    public PisoAdapter(PisoRepository pisoRepository) {
        this.pisoRepository = pisoRepository;
    }

    @Override
    public PisoModel findById(Long id) {
        return pisoRepository.findById(id).map(PisoMapper::toModel).orElse(null);
    }

    @Override
    public PaginacionResponse<PisoModel> findByTorreId(Long torreId, PaginacionRequest request) {
        Page<PisoEntity> page = pisoRepository.findByTorreId(
                torreId, PageRequest.of(request.pagina(), request.tamanio())
        );

        List<PisoModel> modelos = page.getContent().stream().map(PisoMapper::toModel).toList();
        return new PaginacionResponse<>(modelos, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public PisoModel save(PisoModel piso) {
        PisoEntity entity = PisoMapper.toEntity(piso);

        // Enlazar la Torre para evitar error de llave foránea nula
        if (piso.getTorreId() != null) {
            TorreEntity torreEntity = new TorreEntity();
            torreEntity.setId(piso.getTorreId());
            entity.setTorre(torreEntity);
        }

        return PisoMapper.toModel(pisoRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        pisoRepository.deleteById(id);
    }
}