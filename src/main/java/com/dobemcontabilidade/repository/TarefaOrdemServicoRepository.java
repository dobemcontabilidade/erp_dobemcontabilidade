package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TarefaOrdemServico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TarefaOrdemServico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarefaOrdemServicoRepository extends JpaRepository<TarefaOrdemServico, Long> {}
