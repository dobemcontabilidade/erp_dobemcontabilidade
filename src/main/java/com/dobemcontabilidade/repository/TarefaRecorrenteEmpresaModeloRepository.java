package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModelo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TarefaRecorrenteEmpresaModelo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarefaRecorrenteEmpresaModeloRepository extends JpaRepository<TarefaRecorrenteEmpresaModelo, Long> {}
