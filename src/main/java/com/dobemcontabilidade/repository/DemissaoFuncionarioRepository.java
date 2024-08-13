package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DemissaoFuncionario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DemissaoFuncionario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemissaoFuncionarioRepository extends JpaRepository<DemissaoFuncionario, Long> {}
