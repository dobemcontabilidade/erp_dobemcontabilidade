package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PerfilContador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerfilContador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilContadorRepository extends JpaRepository<PerfilContador, Long>, JpaSpecificationExecutor<PerfilContador> {}
