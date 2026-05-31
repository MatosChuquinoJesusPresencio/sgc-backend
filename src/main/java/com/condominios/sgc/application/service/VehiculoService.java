package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.condominios.sgc.application.dto.ActualizarVehiculoRequest;
import com.condominios.sgc.application.dto.CrearVehiculoRequest;
import com.condominios.sgc.application.usecase.ActualizarVehiculoUseCase;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.application.usecase.EliminarVehiculoUseCase;
import com.condominios.sgc.application.usecase.ListarVehiculosUseCase;
import com.condominios.sgc.application.usecase.ObtenerVehiculoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.VehiculoModel;

@Service
@Transactional
public class VehiculoService {

    private final CrearVehiculoUseCase crearVehiculoUseCase;
    private final ActualizarVehiculoUseCase actualizarVehiculoUseCase;
    private final ObtenerVehiculoUseCase obtenerVehiculoUseCase;
    private final EliminarVehiculoUseCase eliminarVehiculoUseCase;
    private final ListarVehiculosUseCase listarVehiculosUseCase;

    public VehiculoService(
            CrearVehiculoUseCase crearVehiculoUseCase,
            ActualizarVehiculoUseCase actualizarVehiculoUseCase,
            ObtenerVehiculoUseCase obtenerVehiculoUseCase,
            EliminarVehiculoUseCase eliminarVehiculoUseCase,
            ListarVehiculosUseCase listarVehiculosUseCase) {
        this.crearVehiculoUseCase = crearVehiculoUseCase;
        this.actualizarVehiculoUseCase = actualizarVehiculoUseCase;
        this.obtenerVehiculoUseCase = obtenerVehiculoUseCase;
        this.eliminarVehiculoUseCase = eliminarVehiculoUseCase;
        this.listarVehiculosUseCase = listarVehiculosUseCase;
    }

    public VehiculoModel crear(CrearVehiculoRequest request) {
        return crearVehiculoUseCase.ejecutar(request);
    }

    public VehiculoModel actualizar(Long id, ActualizarVehiculoRequest request) {
        return actualizarVehiculoUseCase.ejecutar(id, request);
    }

    public VehiculoModel obtener(Long id) {
        return obtenerVehiculoUseCase.ejecutar(id);
    }

    public void eliminar(Long id) {
        eliminarVehiculoUseCase.ejecutar(id);
    }

    public PaginacionResponse<VehiculoModel> listar(PaginacionRequest request) {
        return listarVehiculosUseCase.ejecutar(request);
    }
}
