package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TarefaRecorrenteExecucao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TarefaRecorrenteExecucao entity.
 */
@Repository
public interface TarefaRecorrenteExecucaoRepository extends JpaRepository<TarefaRecorrenteExecucao, Long> {
    default Optional<TarefaRecorrenteExecucao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TarefaRecorrenteExecucao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TarefaRecorrenteExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tarefaRecorrenteExecucao from TarefaRecorrenteExecucao tarefaRecorrenteExecucao left join fetch tarefaRecorrenteExecucao.tarefaRecorrente",
        countQuery = "select count(tarefaRecorrenteExecucao) from TarefaRecorrenteExecucao tarefaRecorrenteExecucao"
    )
    Page<TarefaRecorrenteExecucao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select tarefaRecorrenteExecucao from TarefaRecorrenteExecucao tarefaRecorrenteExecucao left join fetch tarefaRecorrenteExecucao.tarefaRecorrente"
    )
    List<TarefaRecorrenteExecucao> findAllWithToOneRelationships();

    @Query(
        "select tarefaRecorrenteExecucao from TarefaRecorrenteExecucao tarefaRecorrenteExecucao left join fetch tarefaRecorrenteExecucao.tarefaRecorrente where tarefaRecorrenteExecucao.id =:id"
    )
    Optional<TarefaRecorrenteExecucao> findOneWithToOneRelationships(@Param("id") Long id);
}
