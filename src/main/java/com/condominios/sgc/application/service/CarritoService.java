package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.condominios.sgc.application.dto.ActualizarCarritoRequest;
import com.condominios.sgc.application.dto.CrearCarritoRequest;
import com.condominios.sgc.application.usecase.ActualizarCarritoUseCase;
import com.condominios.sgc.application.usecase.CambiarEstadoCarritoUseCase;
import com.condominios.sgc.application.usecase.CrearCarritoUseCase;
import com.condominios.sgc.application.usecase.EliminarCarritoUseCase;
import com.condominios.sgc.application.usecase.ListarCarritosPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerCarritoUseCase;
import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CarritoModel;

@Service
@Transactional
public class CarritoService {

    private final CrearCarritoUseCase crearCarritoUseCase;
    private final ActualizarCarritoUseCase actualizarCarritoUseCase;
    private final CambiarEstadoCarritoUseCase cambiarEstadoCarritoUseCase;
    private final ObtenerCarritoUseCase obtenerCarritoUseCase;
    private final EliminarCarritoUseCase eliminarCarritoUseCase;
    private final ListarCarritosPorCondominioUseCase listarCarritosPorCondominioUseCase;

    public CarritoService(
            CrearCarritoUseCase crearCarritoUseCase,
            ActualizarCarritoUseCase actualizarCarritoUseCase,
            CambiarEstadoCarritoUseCase cambiarEstadoCarritoUseCase,
            ObtenerCarritoUseCase obtenerCarritoUseCase,
            EliminarCarritoUseCase eliminarCarritoUseCase,
            ListarCarritosPorCondominioUseCase listarCarritosPorCondominioUseCase) {
        this.crearCarritoUseCase = crearCarritoUseCase;
        this.actualizarCarritoUseCase = actualizarCarritoUseCase;
        this.cambiarEstadoCarritoUseCase = cambiarEstadoCarritoUseCase;
        this.obtenerCarritoUseCase = obtenerCarritoUseCase;
        this.eliminarCarritoUseCase = eliminarCarritoUseCase;
        this.listarCarritosPorCondominioUseCase = listarCarritosPorCondominioUseCase;
    }

    public CarritoModel crear(CrearCarritoRequest request) {
        return crearCarritoUseCase.ejecutar(request);
    }

    public CarritoModel actualizar(Long id, ActualizarCarritoRequest request) {
        return actualizarCarritoUseCase.ejecutar(id, request);
    }

    public CarritoModel cambiarEstado(Long id, EstadoCarrito nuevoEstado) {
        return cambiarEstadoCarritoUseCase.ejecutar(id, nuevoEstado);
    }

    public CarritoModel obtener(Long id) {
        return obtenerCarritoUseCase.ejecutar(id);
    }

    public void eliminar(Long id) {
        eliminarCarritoUseCase.ejecutar(id);
    }

    public PaginacionResponse<CarritoModel> listarPorCondominio(Long condominioId, PaginacionRequest request) {
        return listarCarritosPorCondominioUseCase.ejecutar(condominioId, request);
    }
}
