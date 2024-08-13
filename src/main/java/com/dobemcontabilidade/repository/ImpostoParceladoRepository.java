package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ImpostoParcelado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ImpostoParcelado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImpostoParceladoRepository extends JpaRepository<ImpostoParcelado, Long> {}
