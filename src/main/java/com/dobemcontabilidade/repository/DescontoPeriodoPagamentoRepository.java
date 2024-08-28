package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DescontoPeriodoPagamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DescontoPeriodoPagamento entity.
 */
@Repository
public interface DescontoPeriodoPagamentoRepository extends JpaRepository<DescontoPeriodoPagamento, Long> {
    default Optional<DescontoPeriodoPagamento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DescontoPeriodoPagamento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DescontoPeriodoPagamento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select descontoPeriodoPagamento from DescontoPeriodoPagamento descontoPeriodoPagamento left join fetch descontoPeriodoPagamento.periodoPagamento left join fetch descontoPeriodoPagamento.planoAssinaturaContabil",
        countQuery = "select count(descontoPeriodoPagamento) from DescontoPeriodoPagamento descontoPeriodoPagamento"
    )
    Page<DescontoPeriodoPagamento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select descontoPeriodoPagamento from DescontoPeriodoPagamento descontoPeriodoPagamento left join fetch descontoPeriodoPagamento.periodoPagamento left join fetch descontoPeriodoPagamento.planoAssinaturaContabil"
    )
    List<DescontoPeriodoPagamento> findAllWithToOneRelationships();

    @Query(
        "select descontoPeriodoPagamento from DescontoPeriodoPagamento descontoPeriodoPagamento left join fetch descontoPeriodoPagamento.periodoPagamento left join fetch descontoPeriodoPagamento.planoAssinaturaContabil where descontoPeriodoPagamento.id =:id"
    )
    Optional<DescontoPeriodoPagamento> findOneWithToOneRelationships(@Param("id") Long id);
}
