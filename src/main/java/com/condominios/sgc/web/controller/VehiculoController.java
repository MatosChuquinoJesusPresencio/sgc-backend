package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.command.ActualizarVehiculoCommand;
import com.condominios.sgc.application.dto.command.CrearVehiculoCommand;
import com.condominios.sgc.application.dto.query.ListarVehiculosQuery;
import com.condominios.sgc.application.usecase.vehiculo.ActualizarVehiculoPorIdUseCase;
import com.condominios.sgc.application.usecase.vehiculo.CrearVehiculoUseCase;
import com.condominios.sgc.application.usecase.vehiculo.EliminarVehiculoPorIdUseCase;
import com.condominios.sgc.application.usecase.vehiculo.ListarVehiculosUseCase;
import com.condominios.sgc.application.usecase.vehiculo.ObtenerVehiculoPorIdUseCase;
import com.condominios.sgc.application.usecase.vehiculo.ObtenerVehiculoPorPlacaUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.web.dto.request.ActualizarVehiculoRequest;
import com.condominios.sgc.web.dto.request.CrearVehiculoRequest;
import com.condominios.sgc.web.dto.response.VehiculoResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/api/vehiculos")
@Validated
public class VehiculoController {

    private final CrearVehiculoUseCase crearVehiculo;
    private final ObtenerVehiculoPorIdUseCase obtenerVehiculo;
    private final ObtenerVehiculoPorPlacaUseCase obtenerVehiculoPorPlaca;
    private final ListarVehiculosUseCase listarVehiculos;
    private final ActualizarVehiculoPorIdUseCase actualizarVehiculo;
    private final EliminarVehiculoPorIdUseCase eliminarVehiculo;
    private final JwtUtil jwtUtil;

    public VehiculoController(CrearVehiculoUseCase crearVehiculo,
            ObtenerVehiculoPorIdUseCase obtenerVehiculo,
            ObtenerVehiculoPorPlacaUseCase obtenerVehiculoPorPlaca,
            ListarVehiculosUseCase listarVehiculos,
            ActualizarVehiculoPorIdUseCase actualizarVehiculo,
            EliminarVehiculoPorIdUseCase eliminarVehiculo,
            JwtUtil jwtUtil) {
        this.crearVehiculo = crearVehiculo;
        this.obtenerVehiculo = obtenerVehiculo;
        this.obtenerVehiculoPorPlaca = obtenerVehiculoPorPlaca;
        this.listarVehiculos = listarVehiculos;
        this.actualizarVehiculo = actualizarVehiculo;
        this.eliminarVehiculo = eliminarVehiculo;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PostMapping
    public ResponseEntity<VehiculoResponse> crear(@RequestBody @Valid CrearVehiculoRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        var usuarioJwt = jwtUtil.extraerUsuario(jwt);
        var idCondominio = usuarioJwt.rol() == Rol.ADMINISTRADOR_CONDOMINIO
                ? usuarioJwt.idCondominio() : request.idCondominio();
        var command = new CrearVehiculoCommand(request.marca(), request.color(), request.modelo(),
                request.placa(), request.tipo(), idCondominio);
        var result = crearVehiculo.ejecutar(command);
        return ResponseEntity.created(URI.create("/api/vehiculos/" + result.id()))
                .body(VehiculoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponse> obtener(@PathVariable Long id) {
        var result = obtenerVehiculo.ejecutar(id);
        return ResponseEntity.ok(VehiculoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/placa/{placa}")
    public ResponseEntity<VehiculoResponse> obtenerPorPlaca(@PathVariable String placa) {
        var result = obtenerVehiculoPorPlaca.ejecutar(placa);
        return ResponseEntity.ok(VehiculoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping
    public ResponseEntity<PaginacionResponse<VehiculoResponse>> listar(
            @RequestParam(defaultValue = "0") @Min(0) int pagina,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int tamano,
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) Long idPropietario,
            @RequestParam(required = false) Long idInquilino,
            @RequestParam(required = false) String marca,
            @AuthenticationPrincipal Jwt jwt) {
        var usuarioJwt = jwtUtil.extraerUsuario(jwt);
        Long idCondominio = null;
        if (usuarioJwt.rol() == Rol.ADMINISTRADOR_CONDOMINIO) {
            idCondominio = usuarioJwt.idCondominio();
        }
        var query = new ListarVehiculosQuery(pagina, tamano, placa, idPropietario,
                idInquilino, marca);
        var result = listarVehiculos.ejecutar(query);
        var contenido = result.contenido().stream()
                .map(VehiculoResponse::desdeAplicacion)
                .toList();
        var webResult = new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
                result.totalElementos(), result.totalPaginas());
        return ResponseEntity.ok(webResult);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponse> actualizar(@PathVariable Long id,
            @RequestBody @Valid ActualizarVehiculoRequest request) {
        var command = new ActualizarVehiculoCommand(request.marca(), request.color(), request.modelo(),
                request.placa(), request.tipo(), request.idPropietario(), request.desasignarPropietario(),
                request.idInquilino(), request.desasignarInquilino(), request.idEstacionamiento(),
                request.desasignarEstacionamiento(), request.idCondominio());
        var result = actualizarVehiculo.ejecutar(id, command);
        return ResponseEntity.ok(VehiculoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarVehiculo.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}
