package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.specification.UsuarioSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class UsuarioAdapter implements UsuarioPort {

    private final UsuarioRepository usuarioRepository;
    private final CondominioRepository condominioRepository;

    public UsuarioAdapter(
            UsuarioRepository usuarioRepository,
            CondominioRepository condominioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    public Optional<UsuarioModel> findById(String id) {
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
            entity.setCondominio(condominioRepository.getReferenceById(model.getCondominioId()));
        }
        var saved = usuarioRepository.save(entity);
        return UsuarioMapper.toModel(saved);
    }

    @Override
    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public PaginacionResponse<UsuarioModel> buscarPaginado(PaginacionRequest request) {
        var orden = request.direccion() != null && request.direccion().equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = request.ordenarPor() != null
                ? PageRequest.of(request.pagina(), request.tamanio(), orden, request.ordenarPor())
                : PageRequest.of(request.pagina(), request.tamanio());
        var spec = UsuarioSpecifications.fromFiltros(request.filtros());
        Page<com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity> page =
                usuarioRepository.findAll(spec, pageable);
        return PaginacionResponse.de(
                page.getContent().stream().map(UsuarioMapper::toModel).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
