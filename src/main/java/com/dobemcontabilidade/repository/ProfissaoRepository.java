package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Profissao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profissao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfissaoRepository extends JpaRepository<Profissao, Long>, JpaSpecificationExecutor<Profissao> {}
