package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.filter.PisoFilter;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.PisoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.PisoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.PisoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class PisoAdapter implements PisoPort {

    private final PisoRepository repository;
    private final PisoMapper mapper;

    public PisoAdapter(PisoRepository repository, PisoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PisoModel guardar(PisoModel piso) {
        PisoEntity entidad = mapper.aEntidad(piso);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<PisoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public List<PisoModel> obtenerTodos() {
        return repository.findAll().stream().map(mapper::aModelo).toList();
    }

    @Override
    public Pagina<PisoModel> obtenerTodos(Paginable paginable, PisoFilter filtro) {
        PageRequest pageRequest = PageRequest.of(paginable.pagina(), paginable.tamano());
        Page<PisoEntity> page = repository.findAll(PisoSpecification.conFiltro(filtro), pageRequest);
        var contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public List<PisoModel> obtenerPorTorre(Long idTorre) {
        return repository.findByIdTorre(idTorre).stream().map(mapper::aModelo).toList();
    }

    @Override
    public int contarPorTorre(Long idTorre) {
        return repository.findByIdTorre(idTorre).size();
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
