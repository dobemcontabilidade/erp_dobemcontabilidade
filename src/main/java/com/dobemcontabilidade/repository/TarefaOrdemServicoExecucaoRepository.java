package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TarefaOrdemServicoExecucao entity.
 */
@Repository
public interface TarefaOrdemServicoExecucaoRepository extends JpaRepository<TarefaOrdemServicoExecucao, Long> {
    default Optional<TarefaOrdemServicoExecucao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TarefaOrdemServicoExecucao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TarefaOrdemServicoExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tarefaOrdemServicoExecucao from TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao left join fetch tarefaOrdemServicoExecucao.tarefaOrdemServico",
        countQuery = "select count(tarefaOrdemServicoExecucao) from TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao"
    )
    Page<TarefaOrdemServicoExecucao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select tarefaOrdemServicoExecucao from TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao left join fetch tarefaOrdemServicoExecucao.tarefaOrdemServico"
    )
    List<TarefaOrdemServicoExecucao> findAllWithToOneRelationships();

    @Query(
        "select tarefaOrdemServicoExecucao from TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao left join fetch tarefaOrdemServicoExecucao.tarefaOrdemServico where tarefaOrdemServicoExecucao.id =:id"
    )
    Optional<TarefaOrdemServicoExecucao> findOneWithToOneRelationships(@Param("id") Long id);
}
