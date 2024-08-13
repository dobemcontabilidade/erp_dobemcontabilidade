package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AgendaTarefaOrdemServicoExecucao entity.
 */
@Repository
public interface AgendaTarefaOrdemServicoExecucaoRepository extends JpaRepository<AgendaTarefaOrdemServicoExecucao, Long> {
    default Optional<AgendaTarefaOrdemServicoExecucao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AgendaTarefaOrdemServicoExecucao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AgendaTarefaOrdemServicoExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select agendaTarefaOrdemServicoExecucao from AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao left join fetch agendaTarefaOrdemServicoExecucao.tarefaOrdemServicoExecucao",
        countQuery = "select count(agendaTarefaOrdemServicoExecucao) from AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao"
    )
    Page<AgendaTarefaOrdemServicoExecucao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select agendaTarefaOrdemServicoExecucao from AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao left join fetch agendaTarefaOrdemServicoExecucao.tarefaOrdemServicoExecucao"
    )
    List<AgendaTarefaOrdemServicoExecucao> findAllWithToOneRelationships();

    @Query(
        "select agendaTarefaOrdemServicoExecucao from AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao left join fetch agendaTarefaOrdemServicoExecucao.tarefaOrdemServicoExecucao where agendaTarefaOrdemServicoExecucao.id =:id"
    )
    Optional<AgendaTarefaOrdemServicoExecucao> findOneWithToOneRelationships(@Param("id") Long id);
}
