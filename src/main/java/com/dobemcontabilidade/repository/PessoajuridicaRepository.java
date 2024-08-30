package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Pessoajuridica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pessoajuridica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoajuridicaRepository extends JpaRepository<Pessoajuridica, Long>, JpaSpecificationExecutor<Pessoajuridica> {}
