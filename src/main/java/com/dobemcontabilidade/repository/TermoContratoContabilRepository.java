package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TermoContratoContabil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TermoContratoContabil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TermoContratoContabilRepository extends JpaRepository<TermoContratoContabil, Long> {}
