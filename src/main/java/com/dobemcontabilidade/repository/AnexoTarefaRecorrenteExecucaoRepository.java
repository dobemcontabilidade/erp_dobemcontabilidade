package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoTarefaRecorrenteExecucao entity.
 */
@Repository
public interface AnexoTarefaRecorrenteExecucaoRepository extends JpaRepository<AnexoTarefaRecorrenteExecucao, Long> {
    default Optional<AnexoTarefaRecorrenteExecucao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoTarefaRecorrenteExecucao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoTarefaRecorrenteExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoTarefaRecorrenteExecucao from AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao left join fetch anexoTarefaRecorrenteExecucao.tarefaRecorrenteExecucao",
        countQuery = "select count(anexoTarefaRecorrenteExecucao) from AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao"
    )
    Page<AnexoTarefaRecorrenteExecucao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select anexoTarefaRecorrenteExecucao from AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao left join fetch anexoTarefaRecorrenteExecucao.tarefaRecorrenteExecucao"
    )
    List<AnexoTarefaRecorrenteExecucao> findAllWithToOneRelationships();

    @Query(
        "select anexoTarefaRecorrenteExecucao from AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao left join fetch anexoTarefaRecorrenteExecucao.tarefaRecorrenteExecucao where anexoTarefaRecorrenteExecucao.id =:id"
    )
    Optional<AnexoTarefaRecorrenteExecucao> findOneWithToOneRelationships(@Param("id") Long id);
}
