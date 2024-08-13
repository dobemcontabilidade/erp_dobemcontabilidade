package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoRequeridoTarefaRecorrente entity.
 */
@Repository
public interface AnexoRequeridoTarefaRecorrenteRepository extends JpaRepository<AnexoRequeridoTarefaRecorrente, Long> {
    default Optional<AnexoRequeridoTarefaRecorrente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoRequeridoTarefaRecorrente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoRequeridoTarefaRecorrente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoRequeridoTarefaRecorrente from AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente left join fetch anexoRequeridoTarefaRecorrente.anexoRequerido left join fetch anexoRequeridoTarefaRecorrente.tarefaRecorrente",
        countQuery = "select count(anexoRequeridoTarefaRecorrente) from AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente"
    )
    Page<AnexoRequeridoTarefaRecorrente> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select anexoRequeridoTarefaRecorrente from AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente left join fetch anexoRequeridoTarefaRecorrente.anexoRequerido left join fetch anexoRequeridoTarefaRecorrente.tarefaRecorrente"
    )
    List<AnexoRequeridoTarefaRecorrente> findAllWithToOneRelationships();

    @Query(
        "select anexoRequeridoTarefaRecorrente from AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente left join fetch anexoRequeridoTarefaRecorrente.anexoRequerido left join fetch anexoRequeridoTarefaRecorrente.tarefaRecorrente where anexoRequeridoTarefaRecorrente.id =:id"
    )
    Optional<AnexoRequeridoTarefaRecorrente> findOneWithToOneRelationships(@Param("id") Long id);
}
