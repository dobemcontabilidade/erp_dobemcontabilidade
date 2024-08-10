package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PrazoAssinatura;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrazoAssinatura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrazoAssinaturaRepository extends JpaRepository<PrazoAssinatura, Long>, JpaSpecificationExecutor<PrazoAssinatura> {}
