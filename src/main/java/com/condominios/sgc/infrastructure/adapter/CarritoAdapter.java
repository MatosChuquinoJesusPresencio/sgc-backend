package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.CarritoFilter;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;
import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.CarritoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CarritoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.CarritoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class CarritoAdapter implements CarritoPort {

    private final CarritoRepository repository;
    private final CarritoMapper mapper;

    public CarritoAdapter(CarritoRepository repository, CarritoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CarritoModel guardar(CarritoModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<CarritoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Optional<CarritoModel> obtenerPorCodigo(String codigo) {
        return repository.findByCodigo(codigo).map(mapper::aModelo);
    }

    @Override
    public PaginacionResponse<CarritoModel> obtenerTodos(PaginacionRequest request, CarritoFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<CarritoEntity> pagina = repository.findAll(CarritoSpecification.conFiltro(filtro), pageable);
        List<CarritoModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
    }

    @Override
    public List<CarritoModel> obtenerPorCondominio(Long idCondominio) {
        return repository.findByIdCondominio(idCondominio).stream().map(mapper::aModelo).toList();
    }

    @Override
    public int contarPorCondominio(Long idCondominio) {
        return (int) repository.count(CarritoSpecification.porCondominio(idCondominio));
    }

    @Override
    public List<CarritoModel> obtenerPorEstado(EstadoCarrito estado) {
        return repository.findByEstado(estado).stream().map(mapper::aModelo).toList();
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
