package com.condominios.sgc.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.ActualizarCondominioRequest;
import com.condominios.sgc.application.dto.CrearCondominioRequest;
import com.condominios.sgc.application.usecase.ActualizarCondominioUseCase;
import com.condominios.sgc.application.usecase.CrearCondominioUseCase;
import com.condominios.sgc.application.usecase.EliminarCondominioUseCase;
import com.condominios.sgc.application.usecase.ListarCondominiosUseCase;
import com.condominios.sgc.application.usecase.ObtenerCondominioUseCase;
import com.condominios.sgc.domain.dto.PageRequestDto;
import com.condominios.sgc.domain.dto.PageResponseDto;
import com.condominios.sgc.web.dto.CondominioResponse;

@RestController
@RequestMapping("/api/condominios")
public class CondominioController {

    private final CrearCondominioUseCase crearCondominioUseCase;
    private final ObtenerCondominioUseCase obtenerCondominioUseCase;
    private final ListarCondominiosUseCase listarCondominiosUseCase;
    private final ActualizarCondominioUseCase actualizarCondominioUseCase;
    private final EliminarCondominioUseCase eliminarCondominioUseCase;

    public CondominioController(
            CrearCondominioUseCase crearCondominioUseCase,
            ObtenerCondominioUseCase obtenerCondominioUseCase,
            ListarCondominiosUseCase listarCondominiosUseCase,
            ActualizarCondominioUseCase actualizarCondominioUseCase,
            EliminarCondominioUseCase eliminarCondominioUseCase) {
        this.crearCondominioUseCase = crearCondominioUseCase;
        this.obtenerCondominioUseCase = obtenerCondominioUseCase;
        this.listarCondominiosUseCase = listarCondominiosUseCase;
        this.actualizarCondominioUseCase = actualizarCondominioUseCase;
        this.eliminarCondominioUseCase = eliminarCondominioUseCase;
    }

    @PostMapping
    public ResponseEntity<CondominioResponse> crear(@RequestBody CrearCondominioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CondominioResponse.fromModel(crearCondominioUseCase.ejecutar(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondominioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
            CondominioResponse.fromModel(obtenerCondominioUseCase.ejecutar(id)));
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<CondominioResponse>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano,
            @RequestParam(defaultValue = "id") String ordenar,
            @RequestParam(defaultValue = "ASC") PageRequestDto.Direccion direccion,
            @RequestParam(required = false) Map<String, String> params) {
        
        params.remove("pagina");
        params.remove("tamano");
        params.remove("ordenar");
        params.remove("direccion");

        PageRequestDto request = new PageRequestDto(pagina, tamano, ordenar, direccion, params);
        
        PageResponseDto<com.condominios.sgc.domain.model.CondominioModel> modelPage = listarCondominiosUseCase.ejecutar(request);
        
        List<CondominioResponse> content = modelPage.contenido().stream()
            .map(CondominioResponse::fromModel)
            .toList();
            
        return ResponseEntity.ok(PageResponseDto.of(
            content,
            modelPage.paginaActual(),
            modelPage.tamanoPagina(),
            modelPage.totalElementos(),
            modelPage.totalPaginas(),
            modelPage.primero(),
            modelPage.ultimo()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondominioResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarCondominioRequest request) {
        return ResponseEntity.ok(
            CondominioResponse.fromModel(actualizarCondominioUseCase.ejecutar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarCondominioUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}
