package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.UsuarioContador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UsuarioContador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioContadorRepository extends JpaRepository<UsuarioContador, Long>, JpaSpecificationExecutor<UsuarioContador> {}
