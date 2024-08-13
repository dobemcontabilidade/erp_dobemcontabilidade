package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.GatewayPagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GatewayPagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GatewayPagamentoRepository extends JpaRepository<GatewayPagamento, Long> {}
