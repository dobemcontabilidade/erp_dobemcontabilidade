package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.SubTarefaOrdemServico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubTarefaOrdemServico entity.
 */
@Repository
public interface SubTarefaOrdemServicoRepository extends JpaRepository<SubTarefaOrdemServico, Long> {
    default Optional<SubTarefaOrdemServico> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SubTarefaOrdemServico> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SubTarefaOrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select subTarefaOrdemServico from SubTarefaOrdemServico subTarefaOrdemServico left join fetch subTarefaOrdemServico.tarefaOrdemServicoExecucao",
        countQuery = "select count(subTarefaOrdemServico) from SubTarefaOrdemServico subTarefaOrdemServico"
    )
    Page<SubTarefaOrdemServico> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select subTarefaOrdemServico from SubTarefaOrdemServico subTarefaOrdemServico left join fetch subTarefaOrdemServico.tarefaOrdemServicoExecucao"
    )
    List<SubTarefaOrdemServico> findAllWithToOneRelationships();

    @Query(
        "select subTarefaOrdemServico from SubTarefaOrdemServico subTarefaOrdemServico left join fetch subTarefaOrdemServico.tarefaOrdemServicoExecucao where subTarefaOrdemServico.id =:id"
    )
    Optional<SubTarefaOrdemServico> findOneWithToOneRelationships(@Param("id") Long id);
}
