package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.CriterioAvaliacaoAtor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CriterioAvaliacaoAtor entity.
 */
@Repository
public interface CriterioAvaliacaoAtorRepository extends JpaRepository<CriterioAvaliacaoAtor, Long> {
    default Optional<CriterioAvaliacaoAtor> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CriterioAvaliacaoAtor> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CriterioAvaliacaoAtor> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select criterioAvaliacaoAtor from CriterioAvaliacaoAtor criterioAvaliacaoAtor left join fetch criterioAvaliacaoAtor.criterioAvaliacao left join fetch criterioAvaliacaoAtor.atorAvaliado",
        countQuery = "select count(criterioAvaliacaoAtor) from CriterioAvaliacaoAtor criterioAvaliacaoAtor"
    )
    Page<CriterioAvaliacaoAtor> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select criterioAvaliacaoAtor from CriterioAvaliacaoAtor criterioAvaliacaoAtor left join fetch criterioAvaliacaoAtor.criterioAvaliacao left join fetch criterioAvaliacaoAtor.atorAvaliado"
    )
    List<CriterioAvaliacaoAtor> findAllWithToOneRelationships();

    @Query(
        "select criterioAvaliacaoAtor from CriterioAvaliacaoAtor criterioAvaliacaoAtor left join fetch criterioAvaliacaoAtor.criterioAvaliacao left join fetch criterioAvaliacaoAtor.atorAvaliado where criterioAvaliacaoAtor.id =:id"
    )
    Optional<CriterioAvaliacaoAtor> findOneWithToOneRelationships(@Param("id") Long id);
}
