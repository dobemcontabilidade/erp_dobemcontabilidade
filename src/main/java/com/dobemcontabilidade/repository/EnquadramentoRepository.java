package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Enquadramento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Enquadramento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnquadramentoRepository extends JpaRepository<Enquadramento, Long> {}
