package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Tributacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tributacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TributacaoRepository extends JpaRepository<Tributacao, Long>, JpaSpecificationExecutor<Tributacao> {}
