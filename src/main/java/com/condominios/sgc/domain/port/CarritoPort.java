package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.filter.CarritoFilter;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;

import java.util.List;
import java.util.Optional;

public interface CarritoPort {
    CarritoModel guardar(CarritoModel carrito);
    Optional<CarritoModel> obtenerPorId(Long id);
    Optional<CarritoModel> obtenerPorCodigo(String codigo);
    Pagina<CarritoModel> obtenerTodos(Paginable paginable, CarritoFilter filtro);
    List<CarritoModel> obtenerPorCondominio(Long idCondominio);
    List<CarritoModel> obtenerPorEstado(EstadoCarrito estado);
    int contarPorCondominio(Long idCondominio);
    void eliminarPorId(Long id);
}

