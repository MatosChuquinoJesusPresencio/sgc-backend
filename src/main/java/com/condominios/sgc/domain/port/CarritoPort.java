package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.CarritoFilter;
import com.condominios.sgc.domain.model.CarritoModel;
import java.util.List;
import java.util.Optional;

public interface CarritoPort {
    CarritoModel guardar(CarritoModel carrito);
    Optional<CarritoModel> obtenerPorId(Long id);
    Optional<CarritoModel> obtenerPorCodigo(String codigo);
    List<CarritoModel> obtenerTodos();
    PaginacionResponse<CarritoModel> obtenerTodos(PaginacionRequest paginacion, CarritoFilter Filter);
    List<CarritoModel> obtenerPorCondominio(Long idCondominio);
    List<CarritoModel> obtenerPorEstado(EstadoCarrito estado);
    void eliminarPorId(Long id);
}

