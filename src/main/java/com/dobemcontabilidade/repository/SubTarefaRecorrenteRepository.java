package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.SubTarefaRecorrente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubTarefaRecorrente entity.
 */
@Repository
public interface SubTarefaRecorrenteRepository extends JpaRepository<SubTarefaRecorrente, Long> {
    default Optional<SubTarefaRecorrente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SubTarefaRecorrente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SubTarefaRecorrente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select subTarefaRecorrente from SubTarefaRecorrente subTarefaRecorrente left join fetch subTarefaRecorrente.tarefaRecorrenteExecucao",
        countQuery = "select count(subTarefaRecorrente) from SubTarefaRecorrente subTarefaRecorrente"
    )
    Page<SubTarefaRecorrente> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select subTarefaRecorrente from SubTarefaRecorrente subTarefaRecorrente left join fetch subTarefaRecorrente.tarefaRecorrenteExecucao"
    )
    List<SubTarefaRecorrente> findAllWithToOneRelationships();

    @Query(
        "select subTarefaRecorrente from SubTarefaRecorrente subTarefaRecorrente left join fetch subTarefaRecorrente.tarefaRecorrenteExecucao where subTarefaRecorrente.id =:id"
    )
    Optional<SubTarefaRecorrente> findOneWithToOneRelationships(@Param("id") Long id);
}
