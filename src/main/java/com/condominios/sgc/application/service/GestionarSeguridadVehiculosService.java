package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.result.SecurityUnassignedVehicleResult;
import com.condominios.sgc.application.dto.result.SecurityVehicleVerificationResult;
import com.condominios.sgc.application.port.in.GestionarSeguridadVehiculosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.domain.shared.exception.VehiculoException;

public class GestionarSeguridadVehiculosService implements GestionarSeguridadVehiculosUseCase {

    private final VehiculoRepositoryPort vehiculoRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final InquilinoRepositoryPort inquilinoRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;

    public GestionarSeguridadVehiculosService(
            VehiculoRepositoryPort vehiculoRepository,
            UsuarioRepositoryPort usuarioRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            InquilinoRepositoryPort inquilinoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
    }

    @Override
    public SecurityVehicleVerificationResult verificarPorPlaca(String placa) {
        var vehiculo = vehiculoRepository.buscarPorPlaca(placa)
            .orElseThrow(VehiculoException::noEncontrado);
        String nombrePropietario = null, torreNombre = null;
        Integer numeroDepartamento = null;
        Boolean derechoEstacionamiento = null;
        Long idEstacionamientoApartamento = null, idApartamento = null;

        if (vehiculo.getIdPropietario() != null) {
            var usuario = usuarioRepository.buscarPorId(vehiculo.getIdPropietario());
            if (usuario.isPresent()) {
                var u = usuario.get();
                nombrePropietario = u.getNombreCompleto().nombres()
                    + " " + u.getNombreCompleto().apellidos();
            }
            var torreInfo = apartamentoRepository.buscarTorreYAptoPorPropietario(vehiculo.getIdPropietario());
            if (torreInfo.isPresent()) {
                torreNombre = torreInfo.get().torreNombre();
                numeroDepartamento = torreInfo.get().numeroDepartamento();
                derechoEstacionamiento = torreInfo.get().derechoEstacionamiento();
                idApartamento = torreInfo.get().idApartamento();
            }
        }

        if (idApartamento == null && vehiculo.getIdInquilino() != null) {
            var inquilinoOpt = inquilinoRepository.buscarPorId(vehiculo.getIdInquilino());
            if (inquilinoOpt.isPresent()) {
                var inquilino = inquilinoOpt.get();
                nombrePropietario = inquilino.getNombreCompleto().nombres()
                    + " " + inquilino.getNombreCompleto().apellidos();
                idApartamento = inquilino.getIdApartamento();
                var torreInfo = apartamentoRepository.buscarTorreYAptoPorId(idApartamento);
                if (torreInfo.isPresent()) {
                    torreNombre = torreInfo.get().torreNombre();
                    numeroDepartamento = torreInfo.get().numeroDepartamento();
                    derechoEstacionamiento = torreInfo.get().derechoEstacionamiento();
                }
            }
        }

        if (idApartamento != null) {
            var slotsDelApto = estacionamientoRepository.buscarPorApartamento(idApartamento);
            if (!slotsDelApto.isEmpty()) {
                idEstacionamientoApartamento = slotsDelApto.get(0).getId();
            }
        }

        var idEstacionamientoVehiculo = vehiculo.getIdEstacionamiento();
        return new SecurityVehicleVerificationResult(
            vehiculo.getId(), vehiculo.getPlaca().valor(),
            vehiculo.getMarca(), vehiculo.getColor(), vehiculo.getModelo(),
            vehiculo.getTipo(), vehiculo.getIdPropietario(), nombrePropietario,
            idEstacionamientoVehiculo, idEstacionamientoApartamento,
            torreNombre, numeroDepartamento, derechoEstacionamiento);
    }

    @Override
    public List<SecurityUnassignedVehicleResult> listarSinEstacionamiento(Long condominioId) {
        return vehiculoRepository.buscarSinEstacionamiento(condominioId)
            .stream()
            .map(v -> {
                String nombrePropietario = null;
                if (v.getIdPropietario() != null) {
                    nombrePropietario = usuarioRepository.buscarPorId(v.getIdPropietario())
                        .map(u -> u.getNombreCompleto().nombres()
                            + " " + u.getNombreCompleto().apellidos())
                        .orElse(null);
                }
                return new SecurityUnassignedVehicleResult(
                    v.getId(), v.getPlaca().valor(), v.getMarca(),
                    v.getModelo(), v.getColor(),
                    v.getTipo() != null ? v.getTipo().name() : null,
                    nombrePropietario);
            })
            .toList();
    }
}
