package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PeriodoPagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PeriodoPagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoPagamentoRepository extends JpaRepository<PeriodoPagamento, Long>, JpaSpecificationExecutor<PeriodoPagamento> {}
