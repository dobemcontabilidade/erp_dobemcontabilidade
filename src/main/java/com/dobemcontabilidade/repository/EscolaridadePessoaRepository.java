package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.EscolaridadePessoa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EscolaridadePessoa entity.
 */
@Repository
public interface EscolaridadePessoaRepository extends JpaRepository<EscolaridadePessoa, Long> {
    default Optional<EscolaridadePessoa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EscolaridadePessoa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EscolaridadePessoa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select escolaridadePessoa from EscolaridadePessoa escolaridadePessoa left join fetch escolaridadePessoa.pessoa left join fetch escolaridadePessoa.escolaridade",
        countQuery = "select count(escolaridadePessoa) from EscolaridadePessoa escolaridadePessoa"
    )
    Page<EscolaridadePessoa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select escolaridadePessoa from EscolaridadePessoa escolaridadePessoa left join fetch escolaridadePessoa.pessoa left join fetch escolaridadePessoa.escolaridade"
    )
    List<EscolaridadePessoa> findAllWithToOneRelationships();

    @Query(
        "select escolaridadePessoa from EscolaridadePessoa escolaridadePessoa left join fetch escolaridadePessoa.pessoa left join fetch escolaridadePessoa.escolaridade where escolaridadePessoa.id =:id"
    )
    Optional<EscolaridadePessoa> findOneWithToOneRelationships(@Param("id") Long id);
}
