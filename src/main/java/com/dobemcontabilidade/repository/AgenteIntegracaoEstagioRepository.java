package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AgenteIntegracaoEstagio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AgenteIntegracaoEstagio entity.
 */
@Repository
public interface AgenteIntegracaoEstagioRepository extends JpaRepository<AgenteIntegracaoEstagio, Long> {
    default Optional<AgenteIntegracaoEstagio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AgenteIntegracaoEstagio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AgenteIntegracaoEstagio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select agenteIntegracaoEstagio from AgenteIntegracaoEstagio agenteIntegracaoEstagio left join fetch agenteIntegracaoEstagio.cidade",
        countQuery = "select count(agenteIntegracaoEstagio) from AgenteIntegracaoEstagio agenteIntegracaoEstagio"
    )
    Page<AgenteIntegracaoEstagio> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select agenteIntegracaoEstagio from AgenteIntegracaoEstagio agenteIntegracaoEstagio left join fetch agenteIntegracaoEstagio.cidade"
    )
    List<AgenteIntegracaoEstagio> findAllWithToOneRelationships();

    @Query(
        "select agenteIntegracaoEstagio from AgenteIntegracaoEstagio agenteIntegracaoEstagio left join fetch agenteIntegracaoEstagio.cidade where agenteIntegracaoEstagio.id =:id"
    )
    Optional<AgenteIntegracaoEstagio> findOneWithToOneRelationships(@Param("id") Long id);
}
