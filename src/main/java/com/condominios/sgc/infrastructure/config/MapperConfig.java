package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.infrastructure.persistence.mapper.ApartamentoMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.CarritoMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.ConfiguracionMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.EstacionamientoMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.InquilinoMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.LogAccesoVehicularMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.LogPrestamoCarritoMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.PisoMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.TokenMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.TorreMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.VehiculoMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.catalog.CiudadMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.catalog.MonedaMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.catalog.PaisMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ApartamentoMapper apartamentoMapper() { return new ApartamentoMapper(); }

    @Bean
    public CarritoMapper carritoMapper() { return new CarritoMapper(); }

    @Bean
    public CondominioMapper condominioMapper() { return new CondominioMapper(); }

    @Bean
    public ConfiguracionMapper configuracionMapper() { return new ConfiguracionMapper(); }

    @Bean
    public EstacionamientoMapper estacionamientoMapper() { return new EstacionamientoMapper(); }

    @Bean
    public InquilinoMapper inquilinoMapper() { return new InquilinoMapper(); }

    @Bean
    public LogAccesoVehicularMapper logAccesoVehicularMapper() { return new LogAccesoVehicularMapper(); }

    @Bean
    public LogPrestamoCarritoMapper logPrestamoCarritoMapper() { return new LogPrestamoCarritoMapper(); }

    @Bean
    public PisoMapper pisoMapper() { return new PisoMapper(); }

    @Bean
    public TokenMapper tokenMapper() { return new TokenMapper(); }

    @Bean
    public TorreMapper torreMapper() { return new TorreMapper(); }

    @Bean
    public UsuarioMapper usuarioMapper() { return new UsuarioMapper(); }

    @Bean
    public VehiculoMapper vehiculoMapper() { return new VehiculoMapper(); }

    @Bean
    public PaisMapper paisMapper() { return new PaisMapper(); }

    @Bean
    public CiudadMapper ciudadMapper() { return new CiudadMapper(); }

    @Bean
    public MonedaMapper monedaMapper() { return new MonedaMapper(); }
}
