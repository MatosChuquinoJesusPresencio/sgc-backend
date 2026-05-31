package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioAdapter implements UsuarioPort {

    private final UsuarioRepository usuarioRepository;
    private final CondominioRepository condominioRepository;

    public UsuarioAdapter(UsuarioRepository usuarioRepository, CondominioRepository condominioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    public Optional<UsuarioModel> findById(Long id) {
        return usuarioRepository.findById(id).map(UsuarioMapper::toModel);
    }

    @Override
    public Optional<UsuarioModel> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).map(UsuarioMapper::toModel);
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public UsuarioModel save(UsuarioModel model) {
        var entity = UsuarioMapper.toEntity(model);
        if (model.getCondominioId() != null) {
            entity.setCondominio(condominioRepository.findById(model.getCondominioId())
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado: " + model.getCondominioId())));
        }
        var saved = usuarioRepository.save(entity);
        return UsuarioMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public PaginacionResponse<UsuarioModel> findAll(PaginacionRequest request) {
        var page = usuarioRepository.findAll(PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(UsuarioMapper::toModel).toList());
    }
}
