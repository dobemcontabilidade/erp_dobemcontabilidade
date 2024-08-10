package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Tarefa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tarefa entity.
 */
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>, JpaSpecificationExecutor<Tarefa> {
    default Optional<Tarefa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Tarefa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Tarefa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tarefa from Tarefa tarefa left join fetch tarefa.esfera left join fetch tarefa.frequencia left join fetch tarefa.competencia",
        countQuery = "select count(tarefa) from Tarefa tarefa"
    )
    Page<Tarefa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select tarefa from Tarefa tarefa left join fetch tarefa.esfera left join fetch tarefa.frequencia left join fetch tarefa.competencia"
    )
    List<Tarefa> findAllWithToOneRelationships();

    @Query(
        "select tarefa from Tarefa tarefa left join fetch tarefa.esfera left join fetch tarefa.frequencia left join fetch tarefa.competencia where tarefa.id =:id"
    )
    Optional<Tarefa> findOneWithToOneRelationships(@Param("id") Long id);
}
