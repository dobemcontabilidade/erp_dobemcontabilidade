package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.FluxoExecucao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FluxoExecucao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FluxoExecucaoRepository extends JpaRepository<FluxoExecucao, Long> {}
