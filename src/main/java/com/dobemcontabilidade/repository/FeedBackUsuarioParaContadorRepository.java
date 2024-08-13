package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.FeedBackUsuarioParaContador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeedBackUsuarioParaContador entity.
 */
@Repository
public interface FeedBackUsuarioParaContadorRepository extends JpaRepository<FeedBackUsuarioParaContador, Long> {
    default Optional<FeedBackUsuarioParaContador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<FeedBackUsuarioParaContador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<FeedBackUsuarioParaContador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select feedBackUsuarioParaContador from FeedBackUsuarioParaContador feedBackUsuarioParaContador left join fetch feedBackUsuarioParaContador.criterioAvaliacaoAtor",
        countQuery = "select count(feedBackUsuarioParaContador) from FeedBackUsuarioParaContador feedBackUsuarioParaContador"
    )
    Page<FeedBackUsuarioParaContador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select feedBackUsuarioParaContador from FeedBackUsuarioParaContador feedBackUsuarioParaContador left join fetch feedBackUsuarioParaContador.criterioAvaliacaoAtor"
    )
    List<FeedBackUsuarioParaContador> findAllWithToOneRelationships();

    @Query(
        "select feedBackUsuarioParaContador from FeedBackUsuarioParaContador feedBackUsuarioParaContador left join fetch feedBackUsuarioParaContador.criterioAvaliacaoAtor where feedBackUsuarioParaContador.id =:id"
    )
    Optional<FeedBackUsuarioParaContador> findOneWithToOneRelationships(@Param("id") Long id);
}
