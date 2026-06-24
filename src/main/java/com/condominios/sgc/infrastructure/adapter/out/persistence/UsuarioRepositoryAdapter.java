package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.type.Rol;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository repository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UsuarioModel> buscarPorId(Long id) {
        return repository.findById(id).map(UsuarioMapper::toModel);
    }

    @Override
    public Optional<UsuarioModel> buscarPorCorreo(String correo) {
        return repository.findByCorreo(correo).map(UsuarioMapper::toModel);
    }

    @Override
    public UsuarioModel guardar(UsuarioModel modelo) {
        return UsuarioMapper.toModel(repository.save(UsuarioMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UsuarioModel> buscarAdministradores(String search, Boolean activo, int pagina, int tamano) {
        var pageable = PageRequest.of(pagina, tamano);
        return repository.buscarAdministradores(search, activo, pageable)
            .map(UsuarioMapper::toModel)
            .toList();
    }

    @Override
    public long contarAdministradores(String search, Boolean activo) {
        return repository.contarAdministradores(search, activo);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return repository.existsByCorreoIgnoreCase(correo);
    }

    @Override
    public List<UsuarioModel> buscarAdministradoresSinCondominio() {
        return repository.findByRolAndIdCondominioIsNull(Rol.ADMINISTRADOR_CONDOMINIO.name())
            .stream()
            .map(UsuarioMapper::toModel)
            .toList();
    }

    @Override
    public Optional<UsuarioModel> buscarPorCondominioId(Long idCondominio) {
        return repository.findByIdCondominio(idCondominio).map(UsuarioMapper::toModel);
    }

    @Override
    public long contarPorRol(String rol) {
        return repository.countByRol(rol);
    }

    @Override
    public List<UsuarioModel> buscarRecientesPorRol(String rol, int limite) {
        var pageable = PageRequest.of(0, limite, Sort.by(Sort.Direction.DESC, "fechaCreacion"));
        return repository.buscarRecientesPorRol(rol, pageable)
            .stream()
            .map(UsuarioMapper::toModel)
            .toList();
    }

    @Override
    public List<UsuarioModel> buscarTodos(String search, String rol, Boolean activo, int pagina, int tamano) {
        var pageable = PageRequest.of(pagina, tamano);
        return repository.buscarTodos(search, rol, activo, pageable)
            .stream()
            .map(UsuarioMapper::toModel)
            .toList();
    }

    @Override
    public long contarTodos(String search, String rol, Boolean activo) {
        return repository.contarTodos(search, rol, activo);
    }

    @Override
    public long contarPorCondominioYRol(Long idCondominio, String rol) {
        return repository.countByIdCondominioAndRol(idCondominio, rol);
    }

    @Override
    public List<UsuarioModel> buscarPorCondominio(Long idCondominio, String search, String rol, Boolean activo, int pagina, int tamano) {
        var pageable = PageRequest.of(pagina, tamano);
        return repository.buscarPorCondominio(idCondominio, search, rol, activo, pageable)
            .stream()
            .map(UsuarioMapper::toModel)
            .toList();
    }

    @Override
    public long contarPorCondominio(Long idCondominio, String search, String rol, Boolean activo) {
        return repository.contarPorCondominio(idCondominio, search, rol, activo);
    }
}
