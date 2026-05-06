package com.condominios.sgc.persistence.repository;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.persistence.entity.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long>,
        JpaSpecificationExecutor<CarritoEntity> {

    Optional<CarritoEntity> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<CarritoEntity> findByCondominioIdAndEstado(Long condominioId, EstadoCarrito estado);
}
