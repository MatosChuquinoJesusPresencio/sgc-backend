package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.dto.ActualizarCondominioRequest;
import com.condominios.sgc.application.dto.CrearCondominioRequest;
import com.condominios.sgc.application.usecase.ActualizarCondominioUseCase;
import com.condominios.sgc.application.usecase.CrearCondominioUseCase;
import com.condominios.sgc.application.usecase.EliminarCondominioUseCase;
import com.condominios.sgc.application.usecase.ListarCondominiosUseCase;
import com.condominios.sgc.application.usecase.ObtenerCondominioUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CondominioModel;

@Service
public class CondominioService {

    private final CrearCondominioUseCase crearCondominioUseCase;
    private final ActualizarCondominioUseCase actualizarCondominioUseCase;
    private final ObtenerCondominioUseCase obtenerCondominioUseCase;
    private final EliminarCondominioUseCase eliminarCondominioUseCase;
    private final ListarCondominiosUseCase listarCondominiosUseCase;

    public CondominioService(
            CrearCondominioUseCase crearCondominioUseCase,
            ActualizarCondominioUseCase actualizarCondominioUseCase,
            ObtenerCondominioUseCase obtenerCondominioUseCase,
            EliminarCondominioUseCase eliminarCondominioUseCase,
            ListarCondominiosUseCase listarCondominiosUseCase) {
        this.crearCondominioUseCase = crearCondominioUseCase;
        this.actualizarCondominioUseCase = actualizarCondominioUseCase;
        this.obtenerCondominioUseCase = obtenerCondominioUseCase;
        this.eliminarCondominioUseCase = eliminarCondominioUseCase;
        this.listarCondominiosUseCase = listarCondominiosUseCase;
    }

    public CondominioModel crear(CrearCondominioRequest request) {
        return crearCondominioUseCase.ejecutar(request);
    }

    public CondominioModel actualizar(Long id, ActualizarCondominioRequest request) {
        return actualizarCondominioUseCase.ejecutar(id, request);
    }

    public CondominioModel obtener(Long id) {
        return obtenerCondominioUseCase.ejecutar(id);
    }

    public void eliminar(Long id) {
        eliminarCondominioUseCase.ejecutar(id);
    }

    public PaginacionResponse<CondominioModel> listar(PaginacionRequest request) {
        return listarCondominiosUseCase.ejecutar(request);
    }
}
