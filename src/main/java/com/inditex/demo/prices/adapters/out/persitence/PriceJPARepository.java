package com.inditex.demo.prices.adapters.out.persitence;

import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.inditex.demo.infraestructure.db.PriceEntity;

import java.time.LocalDateTime;

/**
 * Repositorio reactivo para acceder a precios almacenados en la base de datos.
 */
@Repository
public interface PriceJPARepository extends ReactiveCrudRepository<PriceEntity, Integer> {

    /**
     * Obtiene el precio con mayor prioridad que sea válido para la fecha indicada.
     *
     * @param productId identificador del producto
     * @param brandId   identificador de la marca
     * @param applyDate fecha que debe estar entre startDate y endDate
     * @param endDate   fecha límite para la validez
     * @return precio con mayor prioridad válido para la fecha
     */
    Mono<PriceEntity> findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
            final Integer productId,
            final Integer brandId,
            final LocalDateTime applyDate,
            final LocalDateTime endDate
    );
}
