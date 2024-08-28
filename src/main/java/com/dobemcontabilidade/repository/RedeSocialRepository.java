package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.RedeSocial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RedeSocial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RedeSocialRepository extends JpaRepository<RedeSocial, Long> {}
