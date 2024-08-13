package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.FeedBackContadorParaUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeedBackContadorParaUsuario entity.
 */
@Repository
public interface FeedBackContadorParaUsuarioRepository extends JpaRepository<FeedBackContadorParaUsuario, Long> {
    default Optional<FeedBackContadorParaUsuario> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<FeedBackContadorParaUsuario> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<FeedBackContadorParaUsuario> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select feedBackContadorParaUsuario from FeedBackContadorParaUsuario feedBackContadorParaUsuario left join fetch feedBackContadorParaUsuario.criterioAvaliacaoAtor left join fetch feedBackContadorParaUsuario.contador",
        countQuery = "select count(feedBackContadorParaUsuario) from FeedBackContadorParaUsuario feedBackContadorParaUsuario"
    )
    Page<FeedBackContadorParaUsuario> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select feedBackContadorParaUsuario from FeedBackContadorParaUsuario feedBackContadorParaUsuario left join fetch feedBackContadorParaUsuario.criterioAvaliacaoAtor left join fetch feedBackContadorParaUsuario.contador"
    )
    List<FeedBackContadorParaUsuario> findAllWithToOneRelationships();

    @Query(
        "select feedBackContadorParaUsuario from FeedBackContadorParaUsuario feedBackContadorParaUsuario left join fetch feedBackContadorParaUsuario.criterioAvaliacaoAtor left join fetch feedBackContadorParaUsuario.contador where feedBackContadorParaUsuario.id =:id"
    )
    Optional<FeedBackContadorParaUsuario> findOneWithToOneRelationships(@Param("id") Long id);
}
