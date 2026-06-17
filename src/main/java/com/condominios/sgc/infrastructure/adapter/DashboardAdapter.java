package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.DashboardPort;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.InquilinoMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.LogAccesoVehicularMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.LogPrestamoCarritoMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.EstacionamientoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.InquilinoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.LogAccesoVehicularRepository;
import com.condominios.sgc.infrastructure.persistence.repository.LogPrestamoCarritoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.TorreRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VehiculoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.ApartamentoSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DashboardAdapter implements DashboardPort {

    private final UsuarioRepository usuarioRepository;
    private final CondominioRepository condominioRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final TorreRepository torreRepository;
    private final VehiculoRepository vehiculoRepository;
    private final InquilinoRepository inquilinoRepository;
    private final LogAccesoVehicularRepository logAccesoRepository;
    private final LogPrestamoCarritoRepository logPrestamoRepository;
    private final EstacionamientoRepository estacionamientoRepository;
    private final UsuarioMapper usuarioMapper;
    private final CondominioMapper condominioMapper;
    private final LogAccesoVehicularMapper logAccesoMapper;
    private final LogPrestamoCarritoMapper logPrestamoMapper;
    private final InquilinoMapper inquilinoMapper;

    public DashboardAdapter(UsuarioRepository usuarioRepository,
                            CondominioRepository condominioRepository,
                            ApartamentoRepository apartamentoRepository,
                            TorreRepository torreRepository,
                            VehiculoRepository vehiculoRepository,
                            InquilinoRepository inquilinoRepository,
                            LogAccesoVehicularRepository logAccesoRepository,
                            LogPrestamoCarritoRepository logPrestamoRepository,
                            EstacionamientoRepository estacionamientoRepository,
                            UsuarioMapper usuarioMapper,
                            CondominioMapper condominioMapper,
                            LogAccesoVehicularMapper logAccesoMapper,
                            LogPrestamoCarritoMapper logPrestamoMapper,
                            InquilinoMapper inquilinoMapper) {
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.torreRepository = torreRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.logAccesoRepository = logAccesoRepository;
        this.logPrestamoRepository = logPrestamoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
        this.usuarioMapper = usuarioMapper;
        this.condominioMapper = condominioMapper;
        this.logAccesoMapper = logAccesoMapper;
        this.logPrestamoMapper = logPrestamoMapper;
        this.inquilinoMapper = inquilinoMapper;
    }

    @Override
    public int contarCondominios() {
        return (int) condominioRepository.count();
    }

    @Override
    public int contarApartamentos() {
        return (int) apartamentoRepository.count();
    }

    @Override
    public int contarUsuariosPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol).size();
    }

    @Override
    public int contarTodosLosUsuario() {
        return (int) usuarioRepository.count();
    }

    @Override
    public int contarUsuariosPorActivo(Boolean activo) {
        return (int) usuarioRepository.findAll().stream()
                .filter(u -> u.getActivo().equals(activo))
                .count();
    }

    @Override
    public int contarUsuariosPorRolYCondominio(Rol rol, Long idCondominio) {
        return (int) usuarioRepository.findByIdCondominio(idCondominio).stream()
                .filter(u -> u.getRol() == rol)
                .count();
    }

    @Override
    public int contarApartamentosPorCondominio(Long idCondominio) {
        return (int) apartamentoRepository.count(ApartamentoSpecification.porCondominio(idCondominio));
    }

    @Override
    public int contarApartamentosOcupadosPorCondominio(Long idCondominio) {
        Specification<ApartamentoEntity> spec = (root, query, cb) -> cb.and(
                cb.equal(root.get("piso").get("torre").get("idCondominio"), idCondominio),
                cb.isNotNull(root.get("idPropietario"))
        );
        return (int) apartamentoRepository.count(spec);
    }

    @Override
    public int contarApartamentosDisponiblesPorCondominio(Long idCondominio) {
        Specification<ApartamentoEntity> spec = (root, query, cb) -> cb.and(
                cb.equal(root.get("piso").get("torre").get("idCondominio"), idCondominio),
                cb.isNull(root.get("idPropietario"))
        );
        return (int) apartamentoRepository.count(spec);
    }

    @Override
    public int contarTorresPorCondominio(Long idCondominio) {
        return torreRepository.findByIdCondominio(idCondominio).size();
    }

    @Override
    public int contarInquilinosPorCondominio(Long idCondominio) {
        List<ApartamentoEntity> aptos = apartamentoRepository.findAll(ApartamentoSpecification.porCondominio(idCondominio));
        List<Long> idsApto = aptos.stream().map(ApartamentoEntity::getId).toList();
        if (idsApto.isEmpty()) return 0;
        return inquilinoRepository.findByIdApartamentoIn(idsApto).size();
    }

    @Override
    public int contarVehiculosPorCondominio(Long idCondominio) {
        return vehiculoRepository.findByIdCondominio(idCondominio).size();
    }

    @Override
    public int contarCarritosPrestadosPorCondominio(Long idCondominio) {
        List<ApartamentoEntity> aptos = apartamentoRepository.findAll(ApartamentoSpecification.porCondominio(idCondominio));
        List<Long> idsApto = aptos.stream().map(ApartamentoEntity::getId).toList();
        if (idsApto.isEmpty()) return 0;
        return logPrestamoRepository.findByIdApartamentoInAndFechaDevolucionIsNull(idsApto).size();
    }

    @Override
    public int contarAccesosHoyPorCondominio(Long idCondominio) {
        Instant desde = Instant.now().minus(24, ChronoUnit.HOURS);
        List<Long> idsVehiculo = idsVehiculosPorCondominio(idCondominio);
        if (idsVehiculo.isEmpty()) return 0;
        return (int) logAccesoRepository.countByIdVehiculoInAndFechaEntradaAfter(idsVehiculo, desde);
    }

    @Override
    public int contarVehiculosDentroPorCondominio(Long idCondominio) {
        List<Long> idsVehiculo = idsVehiculosPorCondominio(idCondominio);
        if (idsVehiculo.isEmpty()) return 0;
        return (int) logAccesoRepository.countByIdVehiculoInAndFechaSalidaIsNull(idsVehiculo);
    }

    @Override
    public int contarEstacionamientosDisponiblesPorCondominio(Long idCondominio) {
        return estacionamientoRepository.findByIdCondominioAndDisponibleTrue(idCondominio).size();
    }

    @Override
    public int contarVehiculosPorPropietario(Long idPropietario) {
        return vehiculoRepository.findByIdPropietario(idPropietario).size();
    }

    @Override
    public int contarInquilinosPorPropietario(Long idPropietario) {
        List<ApartamentoEntity> aptos = apartamentoRepository.findByIdPropietario(idPropietario);
        List<Long> idsApto = aptos.stream().map(ApartamentoEntity::getId).toList();
        if (idsApto.isEmpty()) return 0;
        return inquilinoRepository.findByIdApartamentoIn(idsApto).size();
    }

    @Override
    public int contarCarritosPrestadosPorPropietario(Long idPropietario) {
        return (int) logPrestamoRepository.findByIdPropietarioOrderByFechaPrestamoDesc(idPropietario, PageRequest.of(0, Integer.MAX_VALUE))
                .stream().filter(l -> l.getFechaDevolucion() == null).count();
    }

    @Override
    public List<CondominioModel> obtenerUltimosCondominios(int limit) {
        return condominioRepository.findAll(Sort.by(Sort.Direction.DESC, CondominioEntity::getFechaCreacion))
                .stream().limit(limit).map(condominioMapper::aModelo).toList();
    }

    @Override
    public List<UsuarioModel> obtenerUltimosUsuarios(int limit) {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.DESC, UsuarioEntity::getFechaCreacion))
                .stream().limit(limit).map(usuarioMapper::aModelo).toList();
    }

    @Override
    public List<UsuarioModel> obtenerUltimosUsuariosPorCondominio(Long idCondominio, int limit) {
        return usuarioRepository.findByIdCondominio(idCondominio).stream()
                .sorted((a, b) -> b.getFechaCreacion().compareTo(a.getFechaCreacion()))
                .limit(limit).map(usuarioMapper::aModelo).toList();
    }

    @Override
    public List<LogAccesoVehicularModel> obtenerUltimosAccesosPorCondominio(Long idCondominio, int limit) {
        List<Long> idsVehiculo = idsVehiculosPorCondominio(idCondominio);
        if (idsVehiculo.isEmpty()) return List.of();
        return logAccesoRepository.findByIdVehiculoInOrderByFechaEntradaDesc(idsVehiculo, PageRequest.of(0, limit))
                .stream().map(logAccesoMapper::aModelo).toList();
    }

    @Override
    public List<LogPrestamoCarritoModel> obtenerCarritosPrestadosAhoraPorCondominio(Long idCondominio, int limit) {
        List<ApartamentoEntity> aptos = apartamentoRepository.findAll(ApartamentoSpecification.porCondominio(idCondominio));
        List<Long> idsApto = aptos.stream().map(ApartamentoEntity::getId).toList();
        if (idsApto.isEmpty()) return List.of();
        return logPrestamoRepository.findByIdApartamentoInOrderByFechaPrestamoDesc(idsApto, PageRequest.of(0, limit))
                .stream().filter(l -> l.getFechaDevolucion() == null).map(logPrestamoMapper::aModelo).toList();
    }

    @Override
    public List<LogPrestamoCarritoModel> obtenerCarritosPrestadosAhoraPorPropietario(Long idPropietario, int limit) {
        return logPrestamoRepository.findByIdPropietarioOrderByFechaPrestamoDesc(idPropietario, PageRequest.of(0, limit))
                .stream().filter(l -> l.getFechaDevolucion() == null).map(logPrestamoMapper::aModelo).toList();
    }

    @Override
    public List<InquilinoModel> obtenerInquilinosPorPropietario(Long idPropietario) {
        List<ApartamentoEntity> aptos = apartamentoRepository.findByIdPropietario(idPropietario);
        List<Long> idsApto = aptos.stream().map(ApartamentoEntity::getId).toList();
        if (idsApto.isEmpty()) return List.of();
        return inquilinoRepository.findByIdApartamentoIn(idsApto).stream().map(inquilinoMapper::aModelo).toList();
    }

    @Override
    public List<LogAccesoVehicularModel> obtenerAccesosHoyPorPropietario(Long idPropietario) {
        Instant desde = Instant.now().minus(24, ChronoUnit.HOURS);
        List<Long> idsVehiculo = vehiculoRepository.findByIdPropietario(idPropietario).stream()
                .map(VehiculoEntity::getId).toList();
        if (idsVehiculo.isEmpty()) return List.of();
        return logAccesoRepository.findByIdVehiculoInOrderByFechaEntradaDesc(idsVehiculo, PageRequest.of(0, 50))
                .stream().filter(l -> l.getFechaEntrada().isAfter(desde))
                .map(logAccesoMapper::aModelo).toList();
    }

    @Override
    public Map<String, Long> contarUsuariosAgrupadosPorRol() {
        return usuarioRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        u -> u.getRol().name(),
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> contarUsuariosPorMes(Instant desde) {
        return usuarioRepository.findByFechaCreacionAfter(desde).stream()
                .collect(Collectors.groupingBy(
                        u -> {
                            var zdt = u.getFechaCreacion().atZone(java.time.ZoneId.systemDefault());
                            return String.format("%d-%02d", zdt.getYear(), zdt.getMonthValue());
                        },
                        java.util.TreeMap::new,
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> contarInquilinosPorTorre(Long idCondominio) {
        List<ApartamentoEntity> aptos = apartamentoRepository.findAll(ApartamentoSpecification.porCondominio(idCondominio));
        if (aptos.isEmpty()) return Map.of();

        List<Long> idsApto = aptos.stream().map(ApartamentoEntity::getId).toList();
        List<InquilinoEntity> inquilinos = inquilinoRepository.findByIdApartamentoIn(idsApto);

        Map<Long, String> aptoTorre = new HashMap<>();
        for (ApartamentoEntity a : aptos) {
            aptoTorre.put(a.getId(), a.getPiso().getTorre().getNombre());
        }

        Map<String, Long> result = new LinkedHashMap<>();
        for (InquilinoEntity i : inquilinos) {
            String torre = aptoTorre.get(i.getIdApartamento());
            if (torre != null) {
                result.put(torre, result.getOrDefault(torre, 0L) + 1);
            }
        }
        return result;
    }

    @Override
    public List<LogPrestamoCarritoModel> obtenerPrestamosPorPropietario(Long idPropietario) {
        return logPrestamoRepository.findByIdPropietarioOrderByFechaPrestamoDesc(
                        idPropietario, PageRequest.of(0, Integer.MAX_VALUE))
                .stream().map(logPrestamoMapper::aModelo).toList();
    }

    @Override
    public List<LogAccesoVehicularModel> obtenerAccesosVehicularesPorCondominioDesde(Long idCondominio, Instant desde) {
        List<Long> idsVehiculo = idsVehiculosPorCondominio(idCondominio);
        if (idsVehiculo.isEmpty()) return List.of();
        return logAccesoRepository.findByIdVehiculoInOrderByFechaEntradaDesc(
                        idsVehiculo, PageRequest.of(0, Integer.MAX_VALUE))
                .stream().filter(l -> l.getFechaEntrada().isAfter(desde))
                .map(logAccesoMapper::aModelo).toList();
    }

    private List<Long> idsVehiculosPorCondominio(Long idCondominio) {
        return vehiculoRepository.findByIdCondominio(idCondominio).stream()
                .map(VehiculoEntity::getId).toList();
    }
}
