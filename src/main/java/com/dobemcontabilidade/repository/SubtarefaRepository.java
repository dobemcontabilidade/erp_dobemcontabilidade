package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Subtarefa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subtarefa entity.
 */
@Repository
public interface SubtarefaRepository extends JpaRepository<Subtarefa, Long>, JpaSpecificationExecutor<Subtarefa> {
    default Optional<Subtarefa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Subtarefa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Subtarefa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select subtarefa from Subtarefa subtarefa left join fetch subtarefa.tarefa",
        countQuery = "select count(subtarefa) from Subtarefa subtarefa"
    )
    Page<Subtarefa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select subtarefa from Subtarefa subtarefa left join fetch subtarefa.tarefa")
    List<Subtarefa> findAllWithToOneRelationships();

    @Query("select subtarefa from Subtarefa subtarefa left join fetch subtarefa.tarefa where subtarefa.id =:id")
    Optional<Subtarefa> findOneWithToOneRelationships(@Param("id") Long id);
}
