package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.EtapaFluxoExecucao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EtapaFluxoExecucao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapaFluxoExecucaoRepository extends JpaRepository<EtapaFluxoExecucao, Long> {}
