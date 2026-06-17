package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.filter.ApartamentoFilter;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.ApartamentoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.ApartamentoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ApartamentoAdapter implements ApartamentoPort {

    private final ApartamentoRepository repository;
    private final ApartamentoMapper mapper;

    public ApartamentoAdapter(ApartamentoRepository repository, ApartamentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ApartamentoModel guardar(ApartamentoModel apartamento) {
        ApartamentoEntity entidad = mapper.aEntidad(apartamento);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<ApartamentoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Pagina<ApartamentoModel> obtenerTodos(Paginable paginable, ApartamentoFilter filtro) {
        PageRequest pageRequest = PageRequest.of(paginable.pagina(), paginable.tamano());
        Page<ApartamentoEntity> page = repository.findAll(ApartamentoSpecification.conFiltro(filtro), pageRequest);
        var contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public int contarPorCondominio(Long idCondominio) {
        return (int) repository.count(ApartamentoSpecification.porCondominio(idCondominio));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
