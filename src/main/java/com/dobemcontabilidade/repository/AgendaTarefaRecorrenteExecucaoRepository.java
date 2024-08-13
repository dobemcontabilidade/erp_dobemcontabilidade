package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AgendaTarefaRecorrenteExecucao entity.
 */
@Repository
public interface AgendaTarefaRecorrenteExecucaoRepository extends JpaRepository<AgendaTarefaRecorrenteExecucao, Long> {
    default Optional<AgendaTarefaRecorrenteExecucao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AgendaTarefaRecorrenteExecucao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AgendaTarefaRecorrenteExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select agendaTarefaRecorrenteExecucao from AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao left join fetch agendaTarefaRecorrenteExecucao.tarefaRecorrenteExecucao",
        countQuery = "select count(agendaTarefaRecorrenteExecucao) from AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao"
    )
    Page<AgendaTarefaRecorrenteExecucao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select agendaTarefaRecorrenteExecucao from AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao left join fetch agendaTarefaRecorrenteExecucao.tarefaRecorrenteExecucao"
    )
    List<AgendaTarefaRecorrenteExecucao> findAllWithToOneRelationships();

    @Query(
        "select agendaTarefaRecorrenteExecucao from AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao left join fetch agendaTarefaRecorrenteExecucao.tarefaRecorrenteExecucao where agendaTarefaRecorrenteExecucao.id =:id"
    )
    Optional<AgendaTarefaRecorrenteExecucao> findOneWithToOneRelationships(@Param("id") Long id);
}
