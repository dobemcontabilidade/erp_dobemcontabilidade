package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoRequeridoTarefaOrdemServico entity.
 */
@Repository
public interface AnexoRequeridoTarefaOrdemServicoRepository extends JpaRepository<AnexoRequeridoTarefaOrdemServico, Long> {
    default Optional<AnexoRequeridoTarefaOrdemServico> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoRequeridoTarefaOrdemServico> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoRequeridoTarefaOrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoRequeridoTarefaOrdemServico from AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico left join fetch anexoRequeridoTarefaOrdemServico.anexoRequerido left join fetch anexoRequeridoTarefaOrdemServico.tarefaOrdemServico",
        countQuery = "select count(anexoRequeridoTarefaOrdemServico) from AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico"
    )
    Page<AnexoRequeridoTarefaOrdemServico> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select anexoRequeridoTarefaOrdemServico from AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico left join fetch anexoRequeridoTarefaOrdemServico.anexoRequerido left join fetch anexoRequeridoTarefaOrdemServico.tarefaOrdemServico"
    )
    List<AnexoRequeridoTarefaOrdemServico> findAllWithToOneRelationships();

    @Query(
        "select anexoRequeridoTarefaOrdemServico from AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico left join fetch anexoRequeridoTarefaOrdemServico.anexoRequerido left join fetch anexoRequeridoTarefaOrdemServico.tarefaOrdemServico where anexoRequeridoTarefaOrdemServico.id =:id"
    )
    Optional<AnexoRequeridoTarefaOrdemServico> findOneWithToOneRelationships(@Param("id") Long id);
}
