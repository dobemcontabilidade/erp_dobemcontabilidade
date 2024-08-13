package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.GrupoAcessoPadrao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GrupoAcessoPadrao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoAcessoPadraoRepository extends JpaRepository<GrupoAcessoPadrao, Long> {}
