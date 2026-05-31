package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.VehiculoPort;
import com.condominios.sgc.infrastructure.persistence.mapper.VehiculoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.EstacionamientoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.InquilinoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VehiculoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.VehiculoSpecifications;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehiculoAdapter implements VehiculoPort {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InquilinoRepository inquilinoRepository;
    private final EstacionamientoRepository estacionamientoRepository;

    public VehiculoAdapter(VehiculoRepository vehiculoRepository,
                           UsuarioRepository usuarioRepository,
                           InquilinoRepository inquilinoRepository,
                           EstacionamientoRepository estacionamientoRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
    }

    @Override
    public Optional<VehiculoModel> findById(Long id) {
        return vehiculoRepository.findById(id).map(VehiculoMapper::toModel);
    }

    @Override
    public PaginacionResponse<VehiculoModel> findAll(PaginacionRequest request) {
        var page = vehiculoRepository.findAll(PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(VehiculoMapper::toModel).toList());
    }

    @Override
    public PaginacionResponse<VehiculoModel> findByPropietarioId(Long propietarioId, PaginacionRequest request) {
        var spec = VehiculoSpecifications.porPropietarioId(propietarioId);
        var page = vehiculoRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(VehiculoMapper::toModel).toList());
    }

    @Override
    public PaginacionResponse<VehiculoModel> findByInquilinoId(Long inquilinoId, PaginacionRequest request) {
        var spec = VehiculoSpecifications.porInquilinoId(inquilinoId);
        var page = vehiculoRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(VehiculoMapper::toModel).toList());
    }

    @Override
    public VehiculoModel save(VehiculoModel model) {
        var entity = VehiculoMapper.toEntity(model);
        if (model.getPropietarioId() != null) {
            entity.setPropietario(usuarioRepository.findById(model.getPropietarioId())
                .orElseThrow(() -> UsuarioException.noEncontrado()));
        }
        if (model.getInquilinoId() != null) {
            entity.setInquilino(inquilinoRepository.findById(model.getInquilinoId())
                .orElseThrow(() -> InquilinoException.noEncontrado(model.getInquilinoId())));
        }
        if (model.getEstacionamientoId() != null) {
            entity.setEstacionamiento(estacionamientoRepository.findById(model.getEstacionamientoId())
                .orElseThrow(() -> EstacionamientoException.noEncontrado(model.getEstacionamientoId())));
        }
        var saved = vehiculoRepository.save(entity);
        return VehiculoMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        vehiculoRepository.deleteById(id);
    }
}
