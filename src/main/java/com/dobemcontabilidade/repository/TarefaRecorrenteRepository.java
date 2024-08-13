package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TarefaRecorrente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TarefaRecorrente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarefaRecorrenteRepository extends JpaRepository<TarefaRecorrente, Long> {}
