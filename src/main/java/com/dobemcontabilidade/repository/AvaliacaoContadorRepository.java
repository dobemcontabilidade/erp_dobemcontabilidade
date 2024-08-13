package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AvaliacaoContador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AvaliacaoContador entity.
 */
@Repository
public interface AvaliacaoContadorRepository extends JpaRepository<AvaliacaoContador, Long> {
    default Optional<AvaliacaoContador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AvaliacaoContador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AvaliacaoContador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select avaliacaoContador from AvaliacaoContador avaliacaoContador left join fetch avaliacaoContador.contador left join fetch avaliacaoContador.avaliacao",
        countQuery = "select count(avaliacaoContador) from AvaliacaoContador avaliacaoContador"
    )
    Page<AvaliacaoContador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select avaliacaoContador from AvaliacaoContador avaliacaoContador left join fetch avaliacaoContador.contador left join fetch avaliacaoContador.avaliacao"
    )
    List<AvaliacaoContador> findAllWithToOneRelationships();

    @Query(
        "select avaliacaoContador from AvaliacaoContador avaliacaoContador left join fetch avaliacaoContador.contador left join fetch avaliacaoContador.avaliacao where avaliacaoContador.id =:id"
    )
    Optional<AvaliacaoContador> findOneWithToOneRelationships(@Param("id") Long id);
}
