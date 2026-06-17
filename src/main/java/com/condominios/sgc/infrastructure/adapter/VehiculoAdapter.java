package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.filter.VehiculoFilter;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.VehiculoPort;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.VehiculoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.VehiculoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.VehiculoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class VehiculoAdapter implements VehiculoPort {

    private final VehiculoRepository repository;
    private final VehiculoMapper mapper;

    public VehiculoAdapter(VehiculoRepository repository, VehiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public VehiculoModel guardar(VehiculoModel vehiculo) {
        VehiculoEntity entidad = mapper.aEntidad(vehiculo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<VehiculoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Optional<VehiculoModel> obtenerPorPlaca(String placa) {
        return repository.findByPlaca(placa).map(mapper::aModelo);
    }

    @Override
    public List<VehiculoModel> obtenerPorPropietario(Long idPropietario) {
        return repository.findByIdPropietario(idPropietario).stream().map(mapper::aModelo).toList();
    }

    @Override
    public List<VehiculoModel> obtenerPorInquilino(Long idInquilino) {
        return repository.findByIdInquilino(idInquilino).stream().map(mapper::aModelo).toList();
    }

    @Override
    public List<VehiculoModel> obtenerPorCondominio(Long idCondominio) {
        return repository.findByIdCondominio(idCondominio).stream().map(mapper::aModelo).toList();
    }

    @Override
    public int contarPorCondominioYTipo(Long idCondominio, TipoVehiculo tipo) {
        return repository.findByIdCondominio(idCondominio).stream()
                .filter(e -> e.getTipo() == tipo).toList().size();
    }

    @Override
    public int contarPorPropietario(Long idPropietario) {
        return repository.findByIdPropietario(idPropietario).size();
    }

    @Override
    public List<VehiculoModel> obtenerTodos() {
        return repository.findAll().stream().map(mapper::aModelo).toList();
    }

    @Override
    public Pagina<VehiculoModel> obtenerTodos(Paginable paginable, VehiculoFilter filtro) {
        PageRequest pageRequest = PageRequest.of(paginable.pagina(), paginable.tamano());
        Page<VehiculoEntity> page = repository.findAll(VehiculoSpecification.conFiltro(filtro), pageRequest);
        var contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
