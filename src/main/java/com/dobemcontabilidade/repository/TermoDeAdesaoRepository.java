package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TermoDeAdesao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TermoDeAdesao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TermoDeAdesaoRepository extends JpaRepository<TermoDeAdesao, Long>, JpaSpecificationExecutor<TermoDeAdesao> {}
