package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;
import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.LogPrestamoCarritoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.CarritoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.InquilinoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.LogPrestamoCarritoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.specification.LogPrestamoCarritoSpecifications;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LogPrestamoCarritoAdapter implements LogPrestamoCarritoPort {

    private final LogPrestamoCarritoRepository logPrestamoCarritoRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final CarritoRepository carritoRepository;
    private final InquilinoRepository inquilinoRepository;
    private final UsuarioRepository usuarioRepository;

    public LogPrestamoCarritoAdapter(LogPrestamoCarritoRepository logPrestamoCarritoRepository,
                                     ApartamentoRepository apartamentoRepository,
                                     CarritoRepository carritoRepository,
                                     InquilinoRepository inquilinoRepository,
                                     UsuarioRepository usuarioRepository) {
        this.logPrestamoCarritoRepository = logPrestamoCarritoRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.carritoRepository = carritoRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<LogPrestamoCarritoModel> findById(Long id) {
        return logPrestamoCarritoRepository.findById(id).map(LogPrestamoCarritoMapper::toModel);
    }

    @Override
    public PaginacionResponse<LogPrestamoCarritoModel> findByCondominioId(Long condominioId, PaginacionRequest request) {
        var filtros = request.filtros();
        Specification<LogPrestamoCarritoEntity> spec;
        if (filtros != null && !filtros.isEmpty()) {
            spec = LogPrestamoCarritoSpecifications.fromFiltros(filtros);
        } else {
            spec = LogPrestamoCarritoSpecifications.porCondominioId(condominioId);
        }
        var page = logPrestamoCarritoRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(LogPrestamoCarritoMapper::toModel).toList());
    }

    @Override
    public PaginacionResponse<LogPrestamoCarritoModel> findByApartamentoId(Long apartamentoId, PaginacionRequest request) {
        var filtros = request.filtros();
        Specification<LogPrestamoCarritoEntity> spec;
        if (filtros != null && !filtros.isEmpty()) {
            spec = LogPrestamoCarritoSpecifications.fromFiltros(filtros);
        } else {
            spec = LogPrestamoCarritoSpecifications.porApartamentoId(apartamentoId);
        }
        var page = logPrestamoCarritoRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(LogPrestamoCarritoMapper::toModel).toList());
    }

    @Override
    public LogPrestamoCarritoModel save(LogPrestamoCarritoModel model) {
        var entity = LogPrestamoCarritoMapper.toEntity(model);
        if (model.getApartamentoId() != null) {
            entity.setApartamento(apartamentoRepository.findById(model.getApartamentoId())
                .orElseThrow(() -> ApartamentoException.noEncontrado(model.getApartamentoId())));
        }
        if (model.getCarritoId() != null) {
            entity.setCarrito(carritoRepository.findById(model.getCarritoId())
                .orElseThrow(() -> CarritoException.noEncontrado(model.getCarritoId())));
        }
        if (model.getInquilinoId() != null) {
            entity.setInquilino(inquilinoRepository.findById(model.getInquilinoId())
                .orElseThrow(() -> InquilinoException.noEncontrado(model.getInquilinoId())));
        }
        if (model.getPropietarioId() != null) {
            entity.setPropietario(usuarioRepository.findById(model.getPropietarioId())
                .orElseThrow(() -> UsuarioException.noEncontrado()));
        }
        var saved = logPrestamoCarritoRepository.save(entity);
        return LogPrestamoCarritoMapper.toModel(saved);
    }
}
