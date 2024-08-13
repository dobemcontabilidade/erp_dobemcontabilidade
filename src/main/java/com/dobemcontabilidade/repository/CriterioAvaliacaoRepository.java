package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.CriterioAvaliacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CriterioAvaliacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CriterioAvaliacaoRepository extends JpaRepository<CriterioAvaliacao, Long> {}
