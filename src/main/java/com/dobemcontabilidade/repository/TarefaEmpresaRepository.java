package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TarefaEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TarefaEmpresa entity.
 */
@Repository
public interface TarefaEmpresaRepository extends JpaRepository<TarefaEmpresa, Long>, JpaSpecificationExecutor<TarefaEmpresa> {
    default Optional<TarefaEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TarefaEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TarefaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tarefaEmpresa from TarefaEmpresa tarefaEmpresa left join fetch tarefaEmpresa.empresa left join fetch tarefaEmpresa.contador left join fetch tarefaEmpresa.tarefa",
        countQuery = "select count(tarefaEmpresa) from TarefaEmpresa tarefaEmpresa"
    )
    Page<TarefaEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select tarefaEmpresa from TarefaEmpresa tarefaEmpresa left join fetch tarefaEmpresa.empresa left join fetch tarefaEmpresa.contador left join fetch tarefaEmpresa.tarefa"
    )
    List<TarefaEmpresa> findAllWithToOneRelationships();

    @Query(
        "select tarefaEmpresa from TarefaEmpresa tarefaEmpresa left join fetch tarefaEmpresa.empresa left join fetch tarefaEmpresa.contador left join fetch tarefaEmpresa.tarefa where tarefaEmpresa.id =:id"
    )
    Optional<TarefaEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
