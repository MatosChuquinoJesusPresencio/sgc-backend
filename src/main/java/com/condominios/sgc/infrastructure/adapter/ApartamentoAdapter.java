package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.ApartamentoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.PisoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.specification.ApartamentoSpecifications;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApartamentoAdapter implements ApartamentoPort {

    private final ApartamentoRepository apartamentoRepository;
    private final PisoRepository pisoRepository;
    private final UsuarioRepository usuarioRepository;

    public ApartamentoAdapter(ApartamentoRepository apartamentoRepository,
                              PisoRepository pisoRepository,
                              UsuarioRepository usuarioRepository) {
        this.apartamentoRepository = apartamentoRepository;
        this.pisoRepository = pisoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<ApartamentoModel> findById(Long id) {
        return apartamentoRepository.findById(id).map(ApartamentoMapper::toModel);
    }

    @Override
    public PaginacionResponse<ApartamentoModel> findByPisoId(Long pisoId, PaginacionRequest request) {
        var filtros = request.filtros();
        Specification<ApartamentoEntity> spec;
        if (filtros != null && !filtros.isEmpty()) {
            spec = ApartamentoSpecifications.fromFiltros(filtros);
        } else {
            spec = ApartamentoSpecifications.porPisoId(pisoId);
        }
        var page = apartamentoRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(ApartamentoMapper::toModel).toList());
    }

    @Override
    public ApartamentoModel save(ApartamentoModel model) {
        var entity = ApartamentoMapper.toEntity(model);
        entity.setPiso(pisoRepository.findById(model.getPisoId())
            .orElseThrow(() -> PisoException.noEncontrado(model.getPisoId())));
        if (model.getPropietarioId() != null) {
            entity.setPropietario(usuarioRepository.findById(model.getPropietarioId())
                .orElseThrow(() -> UsuarioException.noEncontrado()));
        }
        var saved = apartamentoRepository.save(entity);
        return ApartamentoMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        apartamentoRepository.deleteById(id);
    }

    @Override
    public Optional<ApartamentoModel> findByPropietarioId(Long propietarioId) {
        return apartamentoRepository.findByPropietarioId(propietarioId).map(ApartamentoMapper::toModel);
    }
}
