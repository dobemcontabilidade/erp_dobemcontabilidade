package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Permisao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Permisao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermisaoRepository extends JpaRepository<Permisao, Long> {}
