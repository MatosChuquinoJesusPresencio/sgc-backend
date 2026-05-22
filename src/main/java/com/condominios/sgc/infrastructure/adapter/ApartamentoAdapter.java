package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.ApartamentoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApartamentoAdapter implements ApartamentoPort {
    private final ApartamentoRepository apartamentoRepository;

    public ApartamentoAdapter(ApartamentoRepository apartamentoRepository) {
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    public ApartamentoModel findById(Long id) {
        return apartamentoRepository.findById(id).map(ApartamentoMapper::toModel).orElse(null);
    }

    @Override
    public PaginacionResponse<ApartamentoModel> findByPisoId(Long pisoId, PaginacionRequest request) {
        Page<ApartamentoEntity> page = apartamentoRepository.findByPisoId(
                pisoId, PageRequest.of(request.pagina(), request.tamanio())
        );

        List<ApartamentoModel> modelos = page.getContent().stream().map(ApartamentoMapper::toModel).toList();
        return new PaginacionResponse<>(modelos, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public ApartamentoModel save(ApartamentoModel apartamento) {
        ApartamentoEntity entity = ApartamentoMapper.toEntity(apartamento);

        if (apartamento.getPisoId() != null) {
            PisoEntity pisoEntity = new PisoEntity();
            pisoEntity.setId(apartamento.getPisoId());
            entity.setPiso(pisoEntity);
        }

        if (apartamento.getPropietarioId() != null) {
            UsuarioEntity propietarioEntity = new UsuarioEntity();
            propietarioEntity.setId(apartamento.getPropietarioId());
            entity.setPropietario(propietarioEntity);
        }

        return ApartamentoMapper.toModel(apartamentoRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        apartamentoRepository.deleteById(id);
    }

    @Override
    public ApartamentoModel findByPropietarioId(String propietarioId) {
        return apartamentoRepository.findByPropietarioId(propietarioId).map(ApartamentoMapper::toModel).orElse(null);
    }
}