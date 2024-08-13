package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.InstituicaoEnsino;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InstituicaoEnsino entity.
 */
@Repository
public interface InstituicaoEnsinoRepository extends JpaRepository<InstituicaoEnsino, Long> {
    default Optional<InstituicaoEnsino> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<InstituicaoEnsino> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<InstituicaoEnsino> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select instituicaoEnsino from InstituicaoEnsino instituicaoEnsino left join fetch instituicaoEnsino.cidade",
        countQuery = "select count(instituicaoEnsino) from InstituicaoEnsino instituicaoEnsino"
    )
    Page<InstituicaoEnsino> findAllWithToOneRelationships(Pageable pageable);

    @Query("select instituicaoEnsino from InstituicaoEnsino instituicaoEnsino left join fetch instituicaoEnsino.cidade")
    List<InstituicaoEnsino> findAllWithToOneRelationships();

    @Query(
        "select instituicaoEnsino from InstituicaoEnsino instituicaoEnsino left join fetch instituicaoEnsino.cidade where instituicaoEnsino.id =:id"
    )
    Optional<InstituicaoEnsino> findOneWithToOneRelationships(@Param("id") Long id);
}
