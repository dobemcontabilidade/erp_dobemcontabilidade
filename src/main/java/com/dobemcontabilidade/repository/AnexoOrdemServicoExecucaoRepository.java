package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoOrdemServicoExecucao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoOrdemServicoExecucao entity.
 */
@Repository
public interface AnexoOrdemServicoExecucaoRepository extends JpaRepository<AnexoOrdemServicoExecucao, Long> {
    default Optional<AnexoOrdemServicoExecucao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoOrdemServicoExecucao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoOrdemServicoExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoOrdemServicoExecucao from AnexoOrdemServicoExecucao anexoOrdemServicoExecucao left join fetch anexoOrdemServicoExecucao.tarefaOrdemServicoExecucao",
        countQuery = "select count(anexoOrdemServicoExecucao) from AnexoOrdemServicoExecucao anexoOrdemServicoExecucao"
    )
    Page<AnexoOrdemServicoExecucao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select anexoOrdemServicoExecucao from AnexoOrdemServicoExecucao anexoOrdemServicoExecucao left join fetch anexoOrdemServicoExecucao.tarefaOrdemServicoExecucao"
    )
    List<AnexoOrdemServicoExecucao> findAllWithToOneRelationships();

    @Query(
        "select anexoOrdemServicoExecucao from AnexoOrdemServicoExecucao anexoOrdemServicoExecucao left join fetch anexoOrdemServicoExecucao.tarefaOrdemServicoExecucao where anexoOrdemServicoExecucao.id =:id"
    )
    Optional<AnexoOrdemServicoExecucao> findOneWithToOneRelationships(@Param("id") Long id);
}
